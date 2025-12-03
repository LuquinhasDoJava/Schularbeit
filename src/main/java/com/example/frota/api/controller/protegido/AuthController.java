package com.example.frota.api.controller.protegido;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.frota.application.dto.user.AuthRequest;
import com.example.frota.domain.user.model.User;
import com.example.frota.domain.user.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager; 

    /**
     * Endpoint para Registro de Novos Usuários.
     * Recebe um AuthRequest e cria um novo User no banco.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User userRequest) {
        // Validação de unicidade 
        if (userRepository.existsById(userRequest.getId())) {
            return new ResponseEntity<>("Id já está em uso!", HttpStatus.BAD_REQUEST);
        }

        // Criptografa a senha antes de salvar
        userRequest.setPassword(passwordEncoder.encode(userRequest.getSenha()));
        
        // Salva o novo usuário
        User savedUser = userRepository.save(userRequest);
        
        // Opcional: Gera o token e retorna para o cliente para ele já estar logado
        UserDetails userDetails = savedUser; // Usando o User como UserDetails (ajuste se necessário)
        String token = jwtService.generateToken(userDetails);
        
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.CREATED);
    }

    /**
     * Endpoint para Login e Geração de Token JWT.
     * Recebe um AuthRequest e autentica as credenciais.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        
        // Tenta autenticar o usuário usando o AuthenticationManager do Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.nome(), authRequest.senha())
        );
        
        // Se a autenticação for bem-sucedida
        if (authentication.isAuthenticated()) {
            // Gera o token JWT para o usuário autenticado
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            // Se a autenticação falhar
            return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
}