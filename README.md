# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/maven-plugin/build-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.2/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.2/reference/web/servlet.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.4.2/reference/actuator/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

---

# 📜 Regras do Jogo da Forca Convencional

## 📌 Objetivo
O jogador deve adivinhar a palavra oculta antes de esgotar suas tentativas, sugerindo letras ou a palavra inteira.

## ⚙️ Configuração do Jogo
- O jogo escolhe aleatoriamente uma palavra dentro de uma categoria (se houver).
- A palavra aparece na tela com espaços em branco representando cada letra.
- O jogador recebe um número fixo de tentativas antes de perder (geralmente 6 a 10 erros permitidos).
- O jogo pode exibir uma forca visualmente ou usar contadores de tentativas.

## 🎮 Jogabilidade
- O jogador pode sugerir uma letra de cada vez.
- Se a letra estiver na palavra, ela aparece nas posições corretas.
- Se a letra não estiver na palavra, o jogo reduz o número de tentativas restantes e adiciona um elemento ao desenho da forca.
- O jogador também pode tentar adivinhar a palavra inteira. Se errar, perde o jogo.

## 🏆 Fim do Jogo
- **Vitória 🎉**: Se o jogador revelar todas as letras antes de esgotar as tentativas.
- **Derrota ❌**: Se o jogador esgotar todas as tentativas sem descobrir a palavra.
- O jogo pode oferecer a opção de jogar novamente com uma nova palavra.

## 🔧 Recursos Extras (Opcional)
- **Dicas**: O jogador pode pedir uma dica, reduzindo o número de tentativas restantes.
- **Temporizador ⏳**: O jogador tem um tempo limite para cada jogada.
- **Categorias**: Palavras podem pertencer a temas como "Animais", "Filmes", "Países", etc.

---

# 🎭 Modo Caos – Jogo da Forca

## 📌 Objetivo
O jogador deve adivinhar a palavra oculta antes de esgotar suas tentativas, enfrentando desafios extras onde as letras descobertas podem embaralhar ou sumir a cada 10 segundos.

## ⚙️ Configuração do Jogo
- O jogo escolhe uma palavra aleatória dentro de uma categoria (se houver).
- A palavra aparece na tela com espaços em branco representando cada letra.
- O jogador tem um número fixo de tentativas (ex: 6 a 10 erros permitidos).
- O jogo exibe uma forca ou contador de tentativas.

## 🎮 Jogabilidade
- O jogador pode sugerir uma letra de cada vez.
- Se a letra estiver na palavra, ela aparece nas posições corretas.
- Se a letra não estiver na palavra, o jogo reduz o número de tentativas restantes.
- A cada **10 segundos**, um dos seguintes eventos pode ocorrer:
    - 🔄 **Embaralhamento**: As letras já descobertas trocam de posição na palavra. (Quando o jogador descobrir a letra que fica no lugar da letra que foi embaralhada, ela retorna para sua posição correta)
    - ❌ **Desaparecimento**: Uma ou mais letras já descobertas somem da tela (mas ainda são válidas se o jogador digitá-las novamente).

## 🏆 Fim do Jogo
- **Vitória 🎉**: Se o jogador revelar todas as letras antes de esgotar as tentativas.
- **Derrota ❌**: Se o jogador esgotar todas as tentativas antes de descobrir a palavra.
- O jogo pode exibir uma tela de resumo mostrando quantas letras embaralharam ou desapareceram.

## 🔧 Recursos Extras (Opcional)
- ⏳ **Atraso Inicial**: O primeiro evento de caos só ocorre após 15 segundos para dar uma chance ao jogador.
- ⚠️ **Modo Hardcore**: O caos acontece **a cada 5 segundos** em vez de 10.
- 🔍 **Modo Estratégico**: O jogador pode gastar uma tentativa para “travar” uma letra já descoberta, impedindo que ela suma ou embaralhe.