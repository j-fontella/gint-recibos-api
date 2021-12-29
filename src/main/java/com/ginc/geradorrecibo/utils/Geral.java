package com.ginc.geradorrecibo.utils;

import com.ginc.geradorrecibo.domains.Procedimento;
import com.ginc.geradorrecibo.dtos.request.endereco.EnderecoRequestDTO;
import com.ginc.geradorrecibo.dtos.request.paciente.PacienteRequestDTO;
import com.ginc.geradorrecibo.dtos.request.recibo.ReciboRequestDTO;
import com.ginc.geradorrecibo.dtos.request.servico.ServicoRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioLoginRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioUpdateRequestDTO;
import com.ginc.geradorrecibo.dtos.response.login.UsuarioResponseDTO;
import com.ginc.geradorrecibo.dtos.response.paciente.PacienteResponseDTO;
import com.ginc.geradorrecibo.dtos.response.recibo.ReciboResponseDTO;
import com.ginc.geradorrecibo.dtos.response.servico.ServicoResponseDTO;
import com.ginc.geradorrecibo.instances.PasswordEncoder;
import com.ginc.geradorrecibo.models.endereco.Endereco;
import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.models.recibo.DataProcedimento;
import com.ginc.geradorrecibo.models.recibo.Recibo;
import com.ginc.geradorrecibo.models.servico.Servico;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Geral {

    public static String encriptarStringSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = algorithm.digest(str.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }

    public static String encriptarStringBCrypt(String str){
        BCryptPasswordEncoder passwordEncoder = PasswordEncoder.getInstance();
        return passwordEncoder.encode(str);
    }

    public static boolean validarNumerico(Object o) {
        String s = String.valueOf(o);
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static String converteDataFormatoBrasil(String data){
        String[] dateString = data.split("-");
        if(dateString.length != 3){
            return null;
        }
        String ano = dateString[0];
        String mes = dateString[1];
        String dia = dateString[2];
        return dia+"/"+mes+"/"+ano;
    }

    public static Usuario converterUsuarioRequest(UsuarioRequestDTO usuarioRequestDTO, Endereco end){
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioRequestDTO, Usuario.class);
        usuario.setEndereco(end);
        return usuario;
    }

    public static Usuario converterUsuarioUpdate(UsuarioUpdateRequestDTO usuarioUpdateRequestDTO, Endereco end){
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioUpdateRequestDTO, Usuario.class);
        usuario.setEndereco(end);
        return usuario;
    }

    public static Paciente converterPacienteRequest(PacienteRequestDTO pacienteRequestDTO, Endereco end){
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = modelMapper.map(pacienteRequestDTO, Paciente.class);
        paciente.setUsuario(new Usuario());
        paciente.getUsuario().setPrk(pacienteRequestDTO.getFrkUsuario());
        paciente.setEndereco(end);
        return paciente;
    }

    public static Endereco converterEnderecoRequest(EnderecoRequestDTO enderecoRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(enderecoRequestDTO, Endereco.class);
    }

    public static Servico converterServicoRequest(ServicoRequestDTO servicoRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(servicoRequestDTO, Servico.class);
        servico.setUsuario(new Usuario());
        servico.getUsuario().setPrk(servicoRequestDTO.getFrkUsuario());
        return servico;
    }

    public static String getStrRealizada(Integer qtdProcedimentos, Procedimento procedimento){
        String realizada = "";
        switch (procedimento.getId()){
            case 1:
            case 4:
                realizada = qtdProcedimentos == 1 ? " realizada " : " realizadas ";
                break;
            case 2:
            case 3:
                realizada = qtdProcedimentos == 1 ? " realizado " : " realizados ";
                break;
        }
        return realizada;
    }

    public static String formataCep(String cep){
        if(cep.length() != 8){
            return "";
        }
        return cep.substring(0,5) + "-" + cep.substring(5,8);
    }

    public static String formataCPF(String cpf){
        if(cpf.length() != 11){
            return "";
        }
        return cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11);
    }

    public static boolean isArrayNotNullAndEmpty(List<?> array){
        if(array == null){
            return false;
        }
        return !array.isEmpty();
    }

    public static Recibo converterReciboRequest(ReciboRequestDTO reciboRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        Recibo recibo = modelMapper.map(reciboRequestDTO, Recibo.class);
        Paciente p = new Paciente();
        p.setPrk(reciboRequestDTO.getFrkPaciente());
        Servico s = new Servico();
        s.setPrk(reciboRequestDTO.getFrkServico());
        recibo.setPaciente(p);
        recibo.setServico(s);
        recibo.setDatasProcedimento(new ArrayList<>());
        if(!Objects.isNull(reciboRequestDTO.getDatasProcedimento())){
            for(LocalDate dt : reciboRequestDTO.getDatasProcedimento()){
                DataProcedimento dataProcedimento = new DataProcedimento();
                dataProcedimento.setData(dt);
                recibo.getDatasProcedimento().add(dataProcedimento);
            }
        }
        return recibo;
    }

    public static List<PacienteResponseDTO> converterPacienteResponseToList(List<Paciente> pacienteList){
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<PacienteResponseDTO>>(){}.getType();
        return modelMapper.map(pacienteList, listType);
    }

    public static ReciboResponseDTO converterReciboResponse(Recibo recibo){
        ReciboResponseDTO rco = new ReciboResponseDTO();
        rco.setServico(recibo.getServico().getNome());
        rco.setPaciente(recibo.getPaciente().getNome());
        rco.setValor(recibo.getValor());
        rco.setPrk(recibo.getPrk());
        rco.setData(Geral.converteDataFormatoBrasil(recibo.getData().toString()));
        rco.setTipoProcedimento(recibo.getTipoProcedimento());
        rco.setQtdTipoProcedimento(recibo.getQtdTipoProcedimento());
        return rco;
    }

    public static List<ReciboResponseDTO> converterReciboResponseToList(List<Recibo> reciboList){
        List<ReciboResponseDTO> listDTO = new ArrayList<>();
        for(Recibo r : reciboList){
            listDTO.add(Geral.converterReciboResponse(r));
        }
        return listDTO;
    }

    public static List<ServicoResponseDTO> converterServicoResponseToList(List<Servico> servicoList){
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<ServicoResponseDTO>>(){}.getType();
        return modelMapper.map(servicoList, listType);
    }

    public static UsuarioResponseDTO converterUsuarioResponse(Usuario u){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(u, UsuarioResponseDTO.class);
    }


    public static boolean validarEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static String converterNomeMes(int mes){
        switch (mes){
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Mario";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
        }
        return null;
    }





}
