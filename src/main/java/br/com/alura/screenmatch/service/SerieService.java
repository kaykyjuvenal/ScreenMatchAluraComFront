package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }
    public List<SerieDTO> obterTop5Series(){
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }
    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(s-> new SerieDTO(s.getId(), s.getTitulo(),
                        s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(),s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }
    private SerieDTO converteDados(Serie s){
        return new SerieDTO(s.getId(), s.getTitulo(),
                        s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(),s.getPoster(), s.getSinopse());
    }

    public List<SerieDTO> obterLancamentos(){
        return converteDados(serieRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }
    public SerieDTO obterPorId(Long id){
        Optional<Serie> serie  = serieRepository.findById(id);
        if (serie.isPresent()){
            Serie s =  serie.get();
            return converteDados(s);
        }
        return null;
    }
}
