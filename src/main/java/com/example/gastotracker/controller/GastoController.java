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
    public String listar(Model model, HttpSession session){
        String categoriaFiltro = (String) session.getAttribute("filtroCategoria");
        LocalDate dataInicialFiltro = (LocalDate) session.getAttribute("filtroDataInicial");
        LocalDate dataFinalFiltro = (LocalDate) session.getAttribute("filtroDataFinal");

        List<Gasto> gastos = gastoService.listarGastoFiltro(categoriaFiltro ,dataInicialFiltro, dataFinalFiltro);

        model.addAttribute("gasto", gastos);

        model.addAttribute("categoriaFiltro", categoriaFiltro);
        model.addAttribute("dataInicialFiltro", dataInicialFiltro);
        model.addAttribute("dataFinalFiltro", dataFinalFiltro);

        return "gastos/lista";
    }

    @GetMapping("/limpar")
    public String limparSessao(HttpSession session,
                               @RequestParam(required = false) String filtro){

        switch (filtro){
            case "categoria":
                session.removeAttribute("filtroCategoria");
                break;
            case "dataInicial":
                session.removeAttribute("filtroDataInicial");
                break;
            case "dataFinal":
                session.removeAttribute("filtroDataFinal");
                break;
        }

        return "redirect:/gastos";
    }

    @PostMapping("/filtro")
    public String aplicarFiltro(@RequestParam(required = false) String categoria,
                                @RequestParam(required = false) @DateTimeFormat LocalDate dataInicial,
                                @RequestParam(required = false) @DateTimeFormat LocalDate dataFinal,
                                HttpSession session) {
        session.setAttribute("filtroCategoria", categoria);
        session.setAttribute("filtroDataInicial", dataInicial);
        session.setAttribute("filtroDataFinal", dataFinal);

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
