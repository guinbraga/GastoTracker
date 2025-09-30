package com.example.gastotracker.service;

import com.example.gastotracker.model.Gasto;
import com.example.gastotracker.repository.GastoRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GastoService {
    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> listarTodosGastos(){
        return this.gastoRepository.findAll();
    }

    public List<Gasto> listarGastoFiltro(String categoria, LocalDate dataInicial, LocalDate dataFinal){
        Specification<Gasto> spec = (root, query, criteriaBuilder) -> {
            // lista de condições
            List<Predicate> predicates = new ArrayList<>();

            // filtro de categoria
            if (categoria != null && !categoria.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("categoria"), categoria));
            }

            // filtro de data inicial - data final
            if (dataInicial != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataGasto"), dataInicial.atStartOfDay()));
            }

            if (dataFinal != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataGasto"), dataFinal.atTime(LocalTime.MAX)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return gastoRepository.findAll(spec);
    }

    public Gasto buscarGastoPorId(Long id){
        return this.gastoRepository.findById(id).orElse(null);
    }

    public Gasto adicionarGasto(Gasto g){
        return this.gastoRepository.save(g);
    }

    public void removerGasto(Long id){
        if (gastoRepository.existsById(id)){
            gastoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Gasto não encontrado por id!");
        }
    }

    public Gasto atualizarGasto(Long id, Gasto gastoAtualizado){
        return gastoRepository.findById(id).map(g->{
            g.setValor(gastoAtualizado.getValor());
            g.setDescricao(gastoAtualizado.getDescricao());
            g.setDataGasto(gastoAtualizado.getDataGasto());
            g.setCategoria(gastoAtualizado.getCategoria());
            g.setFormaPagamento(gastoAtualizado.getFormaPagamento());
            return gastoRepository.save(g);
        }).orElseThrow(()-> new RuntimeException("Gasto não encontrado com id " + id));
    }
}
