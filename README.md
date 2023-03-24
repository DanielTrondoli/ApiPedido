# Api Pedido

![GitHub](https://img.shields.io/badge/Java%20-11%2B-red) ![Maven](https://img.shields.io/badge/Spring%20Boot-2.7.9-green) ![Mysql](https://img.shields.io/badge/Mysql-latest-blue) ![Mysql](https://img.shields.io/badge/RabbitMQ-3--management-red)

Uma Api Rest com dois recursos, Produto e Pedido. Que utiliza o banco de dados Mysql para persistência e o RabbitMQ para processamento assíncrono. Esta Api foi construída utilizando Java 11 com Spring boot 2.7.9 junto com Spring JPA e e o Spring AMPQ. Para gerenciar as dependências foi utilizado o Maven. [Arquivo de dependencias](https://github.com/DanielTrondoli/ApiPedido/blob/main/pom.xml).

## Diagrama de Banco de dados
[![Arquivo de dependencias](https://github.com/DanielTrondoli/ApiPedido/blob/main/imagens/Diagrama%20em%20branco.jpeg)](https://github.com/DanielTrondoli/ApiPedido/blob/main/imagens/Diagrama%20em%20branco.jpeg)

## Executando localmente
Para executar o programa localmente é necessário apenas do Docker instalado na máquina.
Basta dar o comando `docker-compose up` no diretório do projeto para executar o arquivo [docker-compose.yml](https://github.com/DanielTrondoli/ApiPedido/blob/main/docker-compose.yml). 

Serão criados três containers, um com o Mysql, outro com o RabbitMQ e por fim o container com a aplicação. Esse último container utiliza como base a imagem do Maven, assim o Docker consegue copiar para dentro da imagem da aplicação o projeto e rodar o comando "mvn clean install" deixando a imagem pronta para ser executada.
Obs: A primeira execução pode demorar alguns minutos.

## Chamando a API
Os recursos da api podem ser acessados através da URI: localhost:8080/api/[nome do recurso]. Existem dois recursos, produto e pedido.

O arquivo [Insomnia_ApiPedido_REST](https://github.com/DanielTrondoli/ApiPedido/blob/main/Insomnia_ApiPedido_REST.json) contém exemplos de Requests que pode ser realizados na Api.

Importar o arquivo para dentro do [Insomnia](https://insomnia.rest/download)

## Desenvolvimento
Para o desenvolvimento pode-se utilizar o banco de dados H2 que roda em memória. Só é necessário remover os '#' das propriedades referentes a ele no [application.properties](https://github.com/DanielTrondoli/ApiPedido/blob/main/src/main/resources/application.properties) e comentar as propriedade do MySql. Porém, ainda será necessário ter o container do RabbitMQ.


