package com.floriculturamonteiro.floricultura.configuration;

import com.floriculturamonteiro.floricultura.repositories.UserAdmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/carrinho/remover/**")
                ) //permite a exclusão dos itens do carrinho no carrinho de compras
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/loginAdm").permitAll() //acesso público ao Login
                        .requestMatchers( "/admin",
                                "/admin/**",
                                "/addFlores/**",
                                "/editar/**",
                                "/deletarFlores/**",
                                "/atualizarFlores/**",
                                "/controleVenda",
                                "/restaurarEstoque/**").hasRole("ADMIN") //restringe o acesso somente a administradores
                        .requestMatchers(HttpMethod.DELETE, "/carrinho/remover/**").permitAll() //permite acesso ao metodo delete no carrinho de compras
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
