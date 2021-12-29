package com.ginc.geradorrecibo.utils;

public class Retorno {
    private Integer ret;
    private String msg;
    private String dado;

    public Retorno(Integer ret, String msg, String dado) {
        this.ret = ret;
        this.msg = msg;
        this.dado = dado;
    }
    public Retorno() {

    }


    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }
}
