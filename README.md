# Teste Técnico - Sistema Backend para CRUD

O objetivo do teste consiste em desenvolver um sistema backend capaz de realizar as operações do CRUD (Create, Read, Update e Delete) para um sistema de gerenciamento de uma livraria. Deve-se catalogar as informações básicas de um livro e de um gênero literário, respeitando a cardinalidade de um para muitos, onde um gênero pode pertencer a vários livros e um livro se relaciona apenas com um gênero.
Foi utilizado o framework Spring Boot com a linguagem Java para desenvolver uma API RESTful com comunicação com o banco de dados in-memory H2 integrado ao próprio framework.

## Modeling
O mapeamento dos endpoints segue o padrão REST com o estilo genérico "api/books" para livros e "api/genres" para os gêneros literários.
As requisições HTTP respeitam o padrão: 
* GET: Responsável pela consulta e obtenção dos dados
* POST: Armazenar novos dados/entidades
* PUT: Atualização de um objeto já existente
* DELETE: Remoção de um objeto existente

