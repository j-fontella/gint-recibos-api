package com.ginc.geradorrecibo.pdf;

import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.models.recibo.DataProcedimento;
import com.ginc.geradorrecibo.models.recibo.Recibo;
import com.ginc.geradorrecibo.utils.Geral;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


public class ReciboPDF {

    private final List<Recibo> recibos;
    private final Usuario usuario;


    public ReciboPDF(List<Recibo> recibos, Usuario usuario) {
        this.recibos = recibos;
        this.usuario = usuario;
    }

    public void gerarHeader(Document document){
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(20);
        font.setColor(Color.BLACK);

        Paragraph header = new Paragraph(usuario.getNome(), font);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        header.setLeading(25);
        document.add(header);


        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(11);

        Paragraph p = new Paragraph(usuario.getProfissao(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setLeading(25);
        document.add(p);

        p = new Paragraph(this.usuario.getConselho() + " : " + this.usuario.getNumeroConselho(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setLeading(25);
        document.add(p);

        p = new Paragraph("CPF: " + Geral.formataCPF(this.usuario.getDocregistro()), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setLeading(25);
        document.add(p);

        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
    }



    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        document.setMargins(60.0F, 60.0F, 36.0F, 36.0F);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        gerarHeader(document);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(11);
        font.setColor(Color.BLACK);

        int count = recibos.size();
        for (Recibo recibo : recibos) {
            DecimalFormat formatter = new DecimalFormat("#.00");
            String procedimento =  "";
            if(Objects.nonNull(recibo.getTipoProcedimento()) && Objects.nonNull(recibo.getQtdTipoProcedimento())){
                String tipoProcedimento = recibo.getQtdTipoProcedimento() > 1 ? recibo.getTipoProcedimento().getPlural() : recibo.getTipoProcedimento().getSingular();
                procedimento = recibo.getQtdTipoProcedimento() + " " + tipoProcedimento + " de ";
            }

            String valor = formatter.format(recibo.getValor());
            String observacao = recibo.getObservacao() == null ? "" : recibo.getObservacao();
            String observacaoSeparador = ".";
            if(recibo.getObservacao() != null || Geral.isArrayNotNullAndEmpty(recibo.getDatasProcedimento())){
                observacaoSeparador = " ";
            }
            String finalizador = !Geral.isArrayNotNullAndEmpty(recibo.getDatasProcedimento()) && recibo.getObservacao() != null ? "." : "";
            String reciboTexto = "Recebi do paciente " + recibo.getPaciente().getNome() +
                                  " registrado no CPF: " + Geral.formataCPF(recibo.getPaciente().getCpf()) +
                                 " residente no endereço: " + recibo.getPaciente().getEndereco().getEnderecoCompleto() +
                                 " o valor de R$" + valor + " referente a " + procedimento + recibo.getServico().getNome()
                                  + observacaoSeparador + observacao + finalizador;
            if(Geral.isArrayNotNullAndEmpty(recibo.getDatasProcedimento())){
                String realizada = Geral.getStrRealizada(recibo.getQtdTipoProcedimento(), recibo.getTipoProcedimento());
                String naData = recibo.getDatasProcedimento().size() == 1 ? "na data referida abaixo:" : "nas datas referidas abaixo:";
                reciboTexto += realizada + naData;
            }
            Paragraph pa = new Paragraph(reciboTexto, font);
            document.add(pa);
            if(Geral.isArrayNotNullAndEmpty(recibo.getDatasProcedimento())){
                document.add( Chunk.NEWLINE );
                for (DataProcedimento dataProcedimento: recibo.getDatasProcedimento()) {
                    String data = Geral.converteDataFormatoBrasil(dataProcedimento.getData().toString());
                    pa = new Paragraph(data, font);
                    pa.setLeading(20);
                    document.add(pa);
                }
            }
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            LocalDate data = recibo.getData();
            String dataCidadeRecibo = recibo.getCidade() + ", " + data.getDayOfMonth() + " de " + Geral.converterNomeMes(data.getMonthValue()) + " de " + data.getYear();
            pa = new Paragraph(dataCidadeRecibo, font);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pa);
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );

            LineSeparator ln = new LineSeparator();
            ln.setAlignment(LineSeparator.ALIGN_CENTER);
            document.add(ln);

            pa = new Paragraph(usuario.getNome(), font);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pa);

            pa = new Paragraph(usuario.getEndereco().getEnderecoCompleto(), font);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pa);
            count--;
            if(count > 0){
                document.newPage();
                gerarHeader(document);
            }

        }


        document.close();

    }
}
