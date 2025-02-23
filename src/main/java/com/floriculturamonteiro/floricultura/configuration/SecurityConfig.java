package com.floriculturamonteiro.floricultura.configuration;

import com.floriculturamonteiro.floricultura.repositories.UserAdmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/loginAdm").permitAll() //acesso público ao Login
                        .requestMatchers( "/admin", // Página do administrador
                                "/admin/**", // Todas as subrotas de /admin
                                "/addFlores", // Adicionar flores
                                "/editar/**", // Editar flores
                                "/deletarFlores/**", // Deletar flores
                                "/atualizarFlores/**", // Atualizar flores
                                "/controleVenda", // Controle de vendas
                                "/restaurarEstoque/**").hasRole("ADMIN") //restringe o acesso somente a administradores
                        .anyRequest().permitAll() // permite acesso a todas as outras páginas
                )
                .formLogin(form -> form
                        .loginPage("/loginAdm") //pagina de login personalizado
                        .defaultSuccessUrl("/admin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/loginAdm?logout") //redireciona para /loginAdm
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/loginAdm?Error") //redireciona para /loginAdm se o acesso for negado
                );
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserAdmRepository userAdmRepository){
        return username -> userAdmRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("usuário não encontrado"));
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //criptografar a senha
    }
}
