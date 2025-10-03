package com.example.usuario.business;


import com.example.usuario.business.Dto.UsuarioDTO;
import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.exceptions.ConflictException;
import com.example.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.example.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    // CRIPTOGRAFAR A SENHA
    private final PasswordEncoder passwordEncoder;



    // ============================================================
    // TODO ==================== SALVAR USUARIO ====================
    // TODO ====================   INICIO       ====================

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

    ///  ESSA VERIFICACAO FOI DECLARADA AQUI FORA PQ VC PODE PRECISAR USAR ELA EM OUTRO LUGAR
    /// SE ESTIVESSE DENTRO DO emailExiste , VC NAO CONSEGUIRIA CHAMAR ELA EM OUTRO LUGAR
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    // TODO =====================   FIM   ==========================


    //  ============================================================
    // TODO ================ BUSCAR USUARIO POR EMAIL ==============
    // TODO ====================   INICIO       ====================

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("EMAIL NÃO ENCONTRADO" + email));
    }
    // TODO =====================   FIM   ==========================


    //  ============================================================
    // TODO ============== DELETA USUARIO POR EMAIL ================
    // TODO ====================   INICIO       ====================

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
    // TODO =====================   FIM   ==========================











}
