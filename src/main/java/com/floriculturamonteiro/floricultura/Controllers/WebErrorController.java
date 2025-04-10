package com.floriculturamonteiro.floricultura.Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class WebErrorController implements ErrorController {

    @RequestMapping
    public String HandleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error",
                        "Página não encontrada");
                model.addAttribute("message",
                        "A página que você está procurando não existe.");
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("error",
                        "Erro interno");
                model.addAttribute("message",
                        "Algo deu errado no servidor");
                return "error/500";
            }
        }
        model.addAttribute("error",
                "Erro");
        model.addAttribute("message",
                "Ocorreu um erro inesperado");
        return "error/generic";
    }
}
