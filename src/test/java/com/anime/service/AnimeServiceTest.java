package com.anime.service;

import com.anime.dtos.request.AnimeRequest;
import com.anime.dtos.response.AnimeResponse;
import com.anime.exception.NaoEncontradoException;
import com.anime.model.AnimeModel;
import com.anime.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AnimeService animeService;

    @Test()
    public void testCriarAnime() {
        // Criar instâncias fictícias para teste
        AnimeRequest animeRequest = new AnimeRequest();
        animeRequest.setTitulo("Meu Anime");

        AnimeModel animeModel = getAnimeModel();

        AnimeResponse animeResponse = new AnimeResponse();
        animeResponse.setTitulo("Meu Anime");

        // Configurar o comportamento dos mocks
        when(modelMapper.map(animeRequest, AnimeModel.class)).thenReturn(animeModel);
        when(animeRepository.save(any(AnimeModel.class))).thenReturn(animeModel);
        when(modelMapper.map(animeModel, AnimeResponse.class)).thenReturn(animeResponse);

        // Executar o método a ser testado
        AnimeResponse response = animeService.criarAnime(animeRequest);

        // Verificar se o resultado está correto
        assertEquals("Meu Anime", response.getTitulo());

        // Verificar se os métodos foram chamados corretamente
        Mockito.verify(modelMapper).map(animeRequest, AnimeModel.class);
        Mockito.verify(animeRepository).save(any(AnimeModel.class));
        Mockito.verify(modelMapper).map(animeModel, AnimeResponse.class);
    }

    @Test
    public void testListarOuFiltragem() {
        // Mock do pageable
        Pageable pageable = PageRequest.of(0, 10);

        // Dados de exemplo
        String genero = "Ação";
        UUID id = UUID.randomUUID();
        AnimeModel animeModel = new AnimeModel();
        animeModel.setId(id);
        animeModel.setTitulo("NARUTO");
        animeModel.setGenero("Ação");

        AnimeResponse animeResponse = new AnimeResponse();
        animeResponse.setId(id);
        animeResponse.setTitulo("NARUTO");
        animeResponse.setGenero("Ação");
        List<AnimeModel> animeModelList = Arrays.asList(animeModel);
        Page<AnimeModel> animeModelsPage = new PageImpl<>(animeModelList, pageable, animeModelList.size());

        // Configuração dos mocks
        lenient().when(animeRepository.findAllByOrderByGeneroAsc(pageable)).thenReturn(animeModelsPage);
        lenient().when(animeRepository.findAllByGeneroContainingOrderByGeneroAsc(eq(genero), any())).thenReturn(animeModelsPage);
        lenient().when(modelMapper.map(any(), eq(AnimeResponse.class))).thenReturn(animeResponse);


        // Execução do método
        Page<AnimeResponse> responsePage = animeService.listarOuFiltragem(genero, pageable);

        // Verificações
        assertNotNull(responsePage);
        assertFalse(responsePage.isEmpty());
        assertEquals(animeModelList.size(), responsePage.getContent().size());
        // Adicione mais verificações conforme necessário

    }


    @Test()
    public void testDeletarAnimeDeveDarErroQuandoOIdNaoFoiEncontrado() {
        // Criar um ID fictício para teste
        UUID id = UUID.randomUUID();

        // Configurar o comportamento do mock do repositório
        when(animeRepository.findById(id)).thenReturn(Optional.empty());

        // Executar o método a ser testado e verificar se a exceção é lançada
        assertThrows(NaoEncontradoException.class, () -> animeService.deletarAnime(id));

        // Verificar se o método do repositório foi chamado corretamente
        Mockito.verify(animeRepository, times(1)).findById(id);
        Mockito.verify(animeRepository, times(0)).deleteById(id);
    }

    @Test()
    public void testDeletarAnimeDeveDeletarOAnimeCorretamente() {
        // Criar um ID fictício para teste
        UUID id = UUID.randomUUID();

        // Criar instâncias fictícias para teste
        AnimeModel animeModel = getAnimeModel();

        // Configurar o comportamento do mock do repositório
        when(animeRepository.findById(id)).thenReturn(Optional.of(animeModel));

        animeService.deletarAnime(id);

        // Verificar se o método do repositório foi chamado corretamente
        Mockito.verify(animeRepository, times(1)).findById(id);
        Mockito.verify(animeRepository, times(1)).deleteById(id);
    }

    private static AnimeModel getAnimeModel() {
        AnimeModel animeModel = new AnimeModel();
        animeModel.setTitulo("Meu Anime");
        return animeModel;
    }
}

