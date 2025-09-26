package com.example.gastotracker.service;

import com.example.gastotracker.model.Gasto;
import com.example.gastotracker.repository.GastoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GastoService {
    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> listarGastos(){
        return this.gastoRepository.findAll();
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
