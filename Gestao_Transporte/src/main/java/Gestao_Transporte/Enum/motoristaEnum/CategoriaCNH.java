package Gestao_Transporte.Enum.motoristaEnum;

import Gestao_Transporte.Enum.TipoVeiculo;

public enum CategoriaCNH {
    C,D,E;

    //Metodo responsável por realizar compatibilidade de categoria com veículo
    public boolean isCompativelCom(TipoVeiculo tipo)
    {
        return switch (this)
        {
            case C -> tipo == TipoVeiculo.CAMINHAO;
            case D -> tipo == TipoVeiculo.ONIBUS;
            case E -> tipo == TipoVeiculo.CARRETA || tipo ==TipoVeiculo.CAMINHAO;
        };
    }
}
