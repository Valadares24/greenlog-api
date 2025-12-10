<img width="1160" height="610" alt="image" src="https://github.com/user-attachments/assets/b890ee5b-d9b5-46f8-a767-7c77b578d519" />


<img width="1312" height="544" alt="image" src="https://github.com/user-attachments/assets/5953da87-8819-4772-8970-a78bda611134" />


<img width="1186" height="520" alt="image" src="https://github.com/user-attachments/assets/2985c4a8-eb7a-46bb-b939-ab4ac04e2ac7" />


**Relatorio de custos de hospedagem em nuvem** 

https://docs.google.com/document/d/1064hSRtt6r411wAkM-TDHYoV75ksE9ZWMTiQMYHi9Jk/edit?usp=sharing



Com certeza. Utilizar 5 padrões de projeto em um trabalho acadêmico de software é um ótimo diferencial.

Aqui está o resumo conciso e objetivo dos 5 padrões de projeto utilizados no seu sistema, focando no propósito e na aplicação específica no GreenLog Solutions.

5 Padrões de Projeto (Design Patterns) - Resumo Objetivo
1. Singleton (Criacional)
Propósito: Garantir que uma classe tenha apenas uma instância e fornecer um ponto de acesso global a ela.

Aplicação no Projeto: Aplicado em CalculoRotaService.java.

Função: Evita que o complexo Grafo da Cidade (estrutura de dados da malha viária) seja recarregado ou reconstruído do zero a cada requisição de rota, otimizando o desempenho do algoritmo de Dijkstra.

2. Repository (Estrutural/Arquitetural)
Propósito: Abstrair a camada de acesso a dados e isolar a lógica de persistência do domínio (regras de negócio).

Aplicação no Projeto: Implementado em interfaces como RotaRepository.java.

Função: Permite que a camada de serviço trabalhe com objetos de domínio (Rota) sem ter conhecimento dos detalhes da tecnologia de banco de dados (JPA, SQL, etc.).

3. DTO (Data Transfer Object) (Estrutural)
Propósito: Encapsular e transportar dados entre subsistemas (Front-end e Back-end) em uma única chamada.

Aplicação no Projeto: Classes como RotaResponse.java e RotaRequest.java.

Função: Garante a segurança ao evitar a exposição direta das Entidades JPA e otimiza o tráfego de rede, enviando apenas os dados necessários para a tela.

4. Strategy (Comportamental)
Propósito: Definir uma família de algoritmos, encapsular cada um deles e torná-los intercambiáveis.

Aplicação no Projeto: Aplicado ao método de busca de caminho em CalculoRotaService.calcularMenorRota().

Função: O algoritmo de Dijkstra é encapsulado como uma estratégia de roteamento. A arquitetura permite que, futuramente, ele possa ser substituído por outro algoritmo (ex: A*) sem alterar as classes que o utilizam.

5. Factory Method (Criacional)
Propósito: Centralizar a lógica complexa de criação de um objeto, delegando a responsabilidade de instanciação para um método específico.

Aplicação no Projeto: Utilizado em métodos como RotaService.mapToEntity().

Função: Encapsula a lógica de montagem da Entidade Rota, garantindo que o objeto seja criado com todos os atributos obrigatórios (incluindo o cálculo da distância total) antes de ser persistido.



**Diagramas de LR*


<img width="900" height="1600" alt="image" src="https://github.com/user-attachments/assets/b38a327a-8035-45fe-9137-f168c96cd7a5" />


<img width="720" height="1280" alt="image" src="https://github.com/user-attachments/assets/84744eee-ef95-4d1b-9000-53e5afa0f23c" />


Tabelas 
AF identificador de placa(regex)

<img width="639" height="175" alt="image" src="https://github.com/user-attachments/assets/6e8204a6-f7fc-4624-ba46-b51966a756d6" />

<img width="577" height="185" alt="image" src="https://github.com/user-attachments/assets/e2b58312-ac8d-4dee-bbfe-1312bd9cb95f" />


<img width="557" height="190" alt="image" src="https://github.com/user-attachments/assets/0eea6161-6738-4188-afcd-feb653a9aec4" />
