package com.example.usuario.business;


import com.example.usuario.business.Dto.EnderecoDTO;
import com.example.usuario.business.Dto.TelefoneDTO;
import com.example.usuario.business.Dto.UsuarioDTO;
import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.exceptions.ConflictException;
import com.example.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.example.usuario.infrastructure.repository.EnderecoRepository;
import com.example.usuario.infrastructure.repository.TelefoneRepository;
import com.example.usuario.infrastructure.repository.UsuarioRepository;
import com.example.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    // CRIPTOGRAFAR A SENHA
    private final PasswordEncoder passwordEncoder;



    /// ============================================================
    /// ======================== SALVAR USUARIO ====================
    /// ========================   INICIO       ====================

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
                usuario = usuarioRepository.save(usuario);
        return  usuarioConverter.paraUsuarioDTO(usuario);
    }


    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    //  ESSA VERIFICACAO FOI DECLARADA AQUI FORA PQ VC PODE PRECISAR USAR ELA EM OUTRO LUGAR
    //  SE ESTIVESSE DENTRO DO emailExiste , VC NAO CONSEGUIRIA CHAMAR ELA EM OUTRO LUGAR
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    ///  =========================   FIM   ==========================


    ///  ============================================================
    ///  ================== BUSCAR USUARIO POR EMAIL ================
    ///  ======================   INICIO   ==========================

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try{
             return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("EMAIL NÃO ENCONTRADO" + email)));

        }    catch (ResourceNotFoundException e) {
             throw new ResourceNotFoundException(("EMAIL NÃO ENCONTRADO" + email));
        }
    }
    ///  =========================   FIM   ==========================


    /// ============================================================
    /// ================== DELETA USUARIO POR EMAIL ================
    /// ========================   INICIO       ====================

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
    ///  =========================   FIM   ==========================

    /// ============================================================
    /// ================== ATUALIZA DADOS DO USUARIO================
    /// ========================   INICIO       ====================
    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto){
        String email            = jwtUtil.extractUsername(token.substring(7)); // Busca o email do usuario atravez do token dele informado

        // Verifica se a senha veio nula no DTO, se veio, manter o que existe, se nao veio, criptografa a nova senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity   = usuarioRepository.findByEmail(email).orElseThrow(
                            () -> new ResourceNotFoundException("EMAIL NÃO ENCONTRADO"));// Busca o usuario no banco de dados

        Usuario usuario         = usuarioConverter.updateUsuario(dto, usuarioEntity);    // Atualiza os dados do usuario conforme o que veio no DTO
        return  usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));         // salva no banco
    }
    ///  =========================   FIM   ==========================

    /// ============================================================
    /// ================== ATUALIZA DADOS DO ENDERECO ==============
    /// ========================   INICIO       ====================
    public EnderecoDTO atualizarEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id de Endereço não encontrado"));


        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }
    ///  =========================   FIM   ==========================

    /// ============================================================
    /// ================== ATUALIZA DADOS DO TELEFONE ==============
    /// ========================   INICIO       ====================
    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO telefoneDTO){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id de Telefone não encontrado"));


        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
    ///  =========================   FIM   ==========================






}
