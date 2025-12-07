# 🚀 logistica-cleancode-at: Refatoração de Módulo de Logística (Assessment)

Este projeto consiste na refatoração completa de um módulo legado de gerenciamento de pedidos de entrega em uma empresa de logística. O objetivo principal foi aplicar os princípios de **Clean Code**, **Engenharia de Software**, e **Arquitetura em Camadas** para resolver problemas crônicos de baixo acoplamento, baixa coesão, ausência de validação e rigidez estrutural.

---

## 💡 Contexto e Objetivo

O código original era uma classe monolítica (`Pedido`) responsável por dados, cálculo de frete, regras promocionais e formatação de saída. A refatoração buscou promover:

1.  **Modularidade e SRP:** Separar responsabilidades em camadas distintas (`model`, `service`, `repository`).
2.  **Extensibilidade (OCP):** Eliminar a rígida estrutura `if-else` para cálculo de frete.
3.  **Robustez:** Implementar imutabilidade e validação explícita no domínio.

---

## ⚙️ Arquitetura e Padrões Aplicados

A solução foi estruturada em camadas, utilizando padrões de design para garantir flexibilidade e manutenibilidade:

### 1. Padrão Strategy para o Cálculo de Frete (OCP)
* **Interface:** `CalculadoraFrete` define o contrato para cálculo.
* **Estratégias Concretas:** `FreteExpresso`, `FretePadrao`, `FreteEconomico` isolam a lógica específica de cada modalidade.
* **Benefício:** Permite a adição de novos tipos de frete sem modificar o código existente (Princípio **Aberto-Fechado**).

### 2. Entidade de Domínio Imutável
* **Classe:** `Entrega` (na camada `model`).
* **Princípio:** A imutabilidade e a validação no construtor garantem que o objeto de domínio seja **sempre criado em um estado válido**, prevenindo falhas silenciosas e promovendo **robustez**.

### 3. Separação de Responsabilidades (SRP)
* **`EtiquetaService`:** Isolado para lidar **apenas** com a apresentação/formatação de texto, utilizando Injeção de Dependência para obter o cálculo (baixo acoplamento).
* **Tratamento de Erros:** Utilização da exceção personalizada `DominioInvalidoException` para tratar entradas inválidas de forma explícita.

---

## 📂 Estrutura do Projeto

O projeto é dividido em três pacotes principais, seguindo as diretrizes de uma arquitetura modular:



---

## 🧪 Testes Automatizados

O projeto inclui uma suíte de testes de unidade com **JUnit 5** para provar a corretude das funcionalidades refatoradas:

* **`EntregaTest.java`:** Valida o contrato de domínio, garantindo que o objeto não pode ser criado com estados inválidos (peso negativo/nulo, etc.).
* **`CalculadoraFreteTest.java`:** Valida todas as regras de cálculo e promocionais em cada estratégia de frete.
* **`EtiquetaServiceTest.java`:** Garante que o serviço coordena as dependências e formata a saída corretamente.

### ➡️ Como Executar

1.  **Clone o repositório.**
2.  **Abra no IntelliJ IDEA** (ou IDE compatível com Maven).
3.  **Carregue as dependências do Maven** (o `pom.xml` inclui o JUnit 5).
4.  Execute a classe `br.com.leonardomuniz.repository.Main` para ver a demonstração.
5.  Execute o diretório `src/test/java` para rodar todos os testes de unidade.

---
*Este projeto foi desenvolvido por [Leonardo Muniz](https://github.com/leonardo-muniz) como parte de um Assessment em Engenharia de Software: Clean Code e Boas Práticas.*