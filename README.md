# Rest-Spring-Security 🕵
Aplicação restful com spring security.

## Learn Application 📓
Essa é uma aplicação criada com o único intuito de aprender sobre o Spring Security, tecnologia poderosa para manter a segurança de uma API Restful. Através desse Readme, eu deixarei alguns conceitos explicados para futuras revisões.

## Basic Auth
Basic Auth é uma das formas de autenticação que temos, para manter a segurança dos requests, suas características são:
- Usuário e senha devem ser enviados em todos os requests.
- Simples e rápido.
- Não é possível realizar um logout.
- Recomenda-se o uso de HTTPS.

## Cross-Site Request Forgery (csrf)
<img src="https://www.infosec.com.br/wp-content/uploads/2017/07/cross-site-request-forgery.png" alt="crsf" />

- O CRSF é a falsificação de solicitação entre sites.
- Com esta forma de invasão, é possível que um usuário malicioso receba o token de autenticação de um outro usuário.
- Através deste token, o usuário malicioso é capaz de realizar requisições sem que seja necessária a autenticação do usuário que teve seu token roubado.
- O Spring Security tem uma própria proteção contra esse tipo de ataque, que pode ser desabilitada através da inclusão da função disable(), em um objeto Http no arquivo de configuração de segurança.
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
	http
	.csrf().disable()
	.authorizeRequests()
	.antMatchers("/").permitAll()
	.antMatchers("/api/**").hasRole(UserRoles.ADMIN.name())
	.anyRequest().authenticated()
	.and()
	.httpBasic();
}
```
