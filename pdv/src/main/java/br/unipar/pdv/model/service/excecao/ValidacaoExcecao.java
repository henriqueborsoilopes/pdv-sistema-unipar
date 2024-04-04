package br.unipar.pdv.model.service.excecao;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoExcecao extends Exception {
    
    private final Map<String, String> erros = new HashMap<>();
    
    public ValidacaoExcecao(String msg) {
        super(msg);
    }
    
    public Map<String, String> getErros() {
        return erros;
    }
    
    public void addErro(String campo, String descricao) {
        this.erros.put(campo, descricao);
    }
}
