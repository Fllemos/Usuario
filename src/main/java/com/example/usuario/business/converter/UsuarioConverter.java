package com.example.usuario.business.converter;

import com.example.usuario.business.Dto.EnderecoDTO;
import com.example.usuario.business.Dto.TelefoneDTO;
import com.example.usuario.business.Dto.UsuarioDTO;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioConverter {

    // ============================================================
    // ==================== I D A  (DTO -> Entidade) ===============
    // ============================================================

    // Converte um UsuarioDTO (entrada da API/camada externa) para a entidade Usuario
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()                                // Inicia o Builder da entidade Usuario
                .nome(usuarioDTO.getNome())                     // Copia o nome do DTO
                .email(usuarioDTO.getEmail())                   // Copia o email do DTO
                .senha(usuarioDTO.getSenha())                   // Copia a senha do DTO
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos())) // Converte lista de EnderecoDTO -> Endereco
                .telefone(paraListaTelefone(usuarioDTO.getTelefone()))    // Converte lista de TelefoneDTO -> Telefone
                .build();                                       // Finaliza a construção do Usuario
    }

    // Converte uma lista de EnderecoDTO para uma lista de Endereco
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOs) {
        List<Endereco> enderecos = new ArrayList<>();           // Cria a lista de retorno
        for (EnderecoDTO enderecoDTO : enderecoDTOs) {          // Itera cada DTO recebido
            enderecos.add(paraEndereco(enderecoDTO));           // Converte e adiciona na lista
        }
        return enderecos;                                       // Retorna a lista convertida
    }

    // Converte um EnderecoDTO para a entidade Endereco
    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()                                // Inicia o Builder de Endereco
                .id(enderecoDTO.getId())
                .rua(enderecoDTO.getRua())                       // Mapeia rua
                .numero(enderecoDTO.getNumero())                 // Mapeia número
                .cidade(enderecoDTO.getCidade())                 // Mapeia cidade
                .complemento(enderecoDTO.getComplemento())       // Mapeia complemento
                .cep(enderecoDTO.getCep())                       // Mapeia CEP
                .estado(enderecoDTO.getEstado())                 // Mapeia estado
                .build();                                        // Finaliza Endereco
    }

    // Converte uma lista de TelefoneDTO para uma lista de Telefone
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        List<Telefone> telefone = new ArrayList<>();             // Cria a lista de retorno
        for (TelefoneDTO telefoneDTO : telefoneDTOS) {           // Itera cada DTO recebido
            telefone.add(paraTelefone(telefoneDTO));             // Converte e adiciona na lista
        }
        return telefone;                                         // Retorna a lista convertida
    }

    // Converte um TelefoneDTO para a entidade Telefone
    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()                                 // Inicia o Builder de Telefone
                .id(telefoneDTO.getId())
                .ddd(telefoneDTO.getDdd())                        // Mapeia DDD
                .numero(telefoneDTO.getNumero())                  // Mapeia número
                .build();                                         // Finaliza Telefone
    }


    // ============================================================
    // =================== V O L T A (Entidade -> DTO) ============
    // ============================================================

    // Converte a entidade Usuario para UsuarioDTO (saída para API/respostas)
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()                               // Inicia o Builder do DTO
                .nome(usuario.getNome())                          // Copia o nome da entidade
                .email(usuario.getEmail())                        // Copia o email da entidade
                .senha(usuario.getSenha())                        // Copia a senha (avalie se deve mesmo retornar)
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos())) // Converte Endereco -> EnderecoDTO
                .telefone(paraListaTelefoneDTO(usuario.getTelefone()))    // Converte Telefone -> TelefoneDTO
                .build();                                          // Finaliza o DTO
    }

    // Converte uma lista de Endereco para uma lista de EnderecoDTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecosEntidade) {
        List<EnderecoDTO> enderecos = new ArrayList<>();          // Cria a lista de retorno
        for (Endereco endereco : enderecosEntidade) {             // Itera cada entidade recebida
            enderecos.add(paraEnderecoDTO(endereco));             // Converte e adiciona na lista
        }
        return enderecos;                                         // Retorna a lista convertida
    }

    // Converte um Endereco (entidade) para EnderecoDTO
    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()                               // Inicia o Builder do DTO
                .id(endereco.getId())
                .rua(endereco.getRua())                            // Mapeia rua
                .numero(endereco.getNumero())                      // Mapeia número
                .cidade(endereco.getCidade())                      // Mapeia cidade
                .complemento(endereco.getComplemento())            // Mapeia complemento
                .cep(endereco.getCep())                            // Mapeia CEP
                .estado(endereco.getEstado())                      // Mapeia estado
                .build();                                          // Finaliza o DTO
    }

    // Converte uma lista de Telefone (entidade) para uma lista de TelefoneDTO
    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefonesEntidade) {
        List<TelefoneDTO> telefone = new ArrayList<>();           // Cria a lista de retorno
        for (Telefone tel : telefonesEntidade) {                  // Itera cada entidade recebida
            telefone.add(paraTelefoneDTO(tel));                   // Converte e adiciona na lista
        }
        return telefone;                                          // Retorna a lista convertida
    }

    // Converte um Telefone (entidade) para TelefoneDTO
    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()                               // Inicia o Builder do DTO
                .id(telefone.getId())
                .ddd(telefone.getDdd())                            // Mapeia DDD
                .numero(telefone.getNumero())                      // Mapeia número
                .build();                                          // Finaliza o DTO
    }


/*
📌 Resumo do fluxo (IDA e VOLTA):

IDA (DTO -> Entidade):
1. paraUsuario(UsuarioDTO) cria um Usuario.
   - Chama paraListaEndereco(List<EnderecoDTO>) -> usa paraEndereco(EnderecoDTO).
   - Chama paraListaTelefone(List<TelefoneDTO>) -> usa paraTelefone(TelefoneDTO).
2. Resultado: Usuario completo (com endereços e telefones) pronto para regra de negócio/persistência.

VOLTA (Entidade -> DTO):
1. paraUsuarioDTO(Usuario) cria um UsuarioDTO.
   - Chama paraListaEnderecoDTO(List<Endereco>) -> usa paraEnderecoDTO(Endereco).
   - Chama paraListaTelefoneDTO(List<Telefone>) -> usa paraTelefoneDTO(Telefone).
2. Resultado: UsuarioDTO pronto para ser devolvido na API/resposta.

➡️ Em resumo: o Converter faz o mapeamento bidirecional entre DTOs (fronteira da aplicação)
   e Entidades (camada de domínio/persistência), delegando as conversões de listas
   para métodos auxiliares que convertem item a item.
*/

    /// ============================================================
    /// ================== tratamente para atualização dos dados do usuario
    /// ============================================================
    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {

            entity.setNome(Optional.ofNullable(usuarioDTO.getNome())
                    .orElse(entity.getNome()));

            entity.setSenha(Optional.ofNullable(usuarioDTO.getSenha())
                    .orElse(entity.getSenha()));

            entity.setEmail(Optional.ofNullable(usuarioDTO.getEmail())
                    .orElse(entity.getEmail()));

            // Mantém sempre o que já existe no banco:
            entity.setEnderecos(entity.getEnderecos());
            entity.setTelefone(entity.getTelefone());

            return entity;
        }
    ///  =========================   FIM   ==========================

    /// ============================================================
    /// ================== ATUALIZA DADOS DO ENDERECO ==============
    /// ========================   INICIO       ====================
    public Endereco updateEndereco(EnderecoDTO dto, Endereco entityEndereco) {

        entityEndereco.setRua(Optional.ofNullable(dto.getRua())
                .orElse(entityEndereco.getRua()));

        entityEndereco.setNumero(Optional.ofNullable(dto.getNumero())
                .orElse(entityEndereco.getNumero()));

        entityEndereco.setComplemento(Optional.ofNullable(dto.getComplemento())
                .orElse(entityEndereco.getComplemento()));

        entityEndereco.setCidade(Optional.ofNullable(dto.getCidade())
                .orElse(entityEndereco.getCidade()));

        entityEndereco.setEstado(Optional.ofNullable(dto.getEstado())
                .orElse(entityEndereco.getEstado()));

        entityEndereco.setCep(Optional.ofNullable(dto.getCep())
                .orElse(entityEndereco.getCep()));

        return entityEndereco;
    }
    ///  =========================   FIM   ==========================

    /// ============================================================
    /// ================== ATUALIZA DADOS DO TELEFONE ==============
    /// ========================   INICIO       ====================
    public Telefone updateTelefone(TelefoneDTO dto, Telefone entityTelefone) {
        entityTelefone.setNumero(Optional.ofNullable(dto.getNumero())
                .orElse(entityTelefone.getNumero()));

        entityTelefone.setDdd(Optional.ofNullable(dto.getDdd())
                .orElse(entityTelefone.getDdd()));

        return entityTelefone;
    }
}















