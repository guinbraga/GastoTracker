package com.example.gastotracker.controller;

import com.example.gastotracker.model.Gasto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import com.example.gastotracker.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/gastos")
public class GastoController {
    private final GastoService gastoService;

    @Autowired
    public GastoController(GastoService gastoService) {this.gastoService = gastoService;}

    @GetMapping
    public String listar(Model model,
                         HttpSession session,
                         @RequestParam(required = false) String categoria,
                         @RequestParam(required = false) @DateTimeFormat LocalDate dataInicial,
                         @RequestParam(required = false) @DateTimeFormat LocalDate dataFinal){
        String categoriaFiltro = (String) session.getAttribute("filtro");
        List<Gasto> gastos;
        if (categoriaFiltro != null && !categoriaFiltro.isEmpty()){
            gastos = gastoService.listarGastoFiltro(categoria,dataInicial,dataFinal);
        } else {
            gastos = gastoService.listarGastoFiltro(categoria, dataInicial, dataFinal);
        }

        model.addAttribute("gasto", gastos);
        model.addAttribute("categoriaFiltro", categoria);
        model.addAttribute("dataInicialFiltro", dataInicial);
        model.addAttribute("dataFinalFiltro", dataFinal);

        return "gastos/lista";
    }

    @GetMapping("/gastos/limpar")
    public String limparSessao(HttpSession session){
        session.invalidate();
        return "redirect:/gastos";
    }

    @PostMapping("/filtro")
    public String aplicarFiltro(@RequestParam String categoria, HttpSession session) {
        session.setAttribute("filtro", categoria);
        return "redirect:/gastos";
    }

    @GetMapping("/novo")
    public String abrirCadastro(Model model){
        model.addAttribute("gasto", new Gasto());
        return "gastos/form";
    }

    @PostMapping
    public String adicionarGasto(@Valid @ModelAttribute Gasto gasto, BindingResult erros, RedirectAttributes ra){
        if (erros.hasErrors()){
            return "gastos/form";
        }
        try {
            gastoService.adicionarGasto(gasto);} catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        ra.addFlashAttribute("msg", "Gasto adicionado com sucesso!");
        return "redirect:/gastos";
    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("gasto", gastoService.buscarGastoPorId(id));
        return "gastos/form";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute Gasto gasto,
                            BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            return "gastos/form";
        }
        gastoService.atualizarGasto(id, gasto);
        ra.addFlashAttribute("msg", "Gasto atualizado!");
        return "redirect:/gastos";
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id, RedirectAttributes ra) {
        gastoService.removerGasto(id);
        ra.addFlashAttribute("msg", "Gasto removido com sucesso!");
        return "redirect:/gastos";
    }


}
