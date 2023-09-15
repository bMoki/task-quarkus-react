# Quarkus API e React Frontend

Este repositório contém tanto uma API REST em Quarkus quanto um frontend React.js para gerenciamento de tarefas.

## Sumário

- [Início Rápido](#início-rápido)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
- [Executando a API Quarkus](#executando-a-api-quarkus)
- [Executando o Frontend React.js](#executando-o-frontend-reactjs)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Contato](#contato)

## Início Rápido

Estas instruções ajudarão você a configurar e executar a API Quarkus e o frontend React.js em sua máquina local.

### Pré-requisitos

Antes de começar, certifique-se de que os seguintes pré-requisitos estejam instalados:

- [Java Development Kit (JDK) 17](https://adoptopenjdk.net/)
- [Apache Maven](https://maven.apache.org/download.cgi) (para construir a API Quarkus)
- [Node.js](https://nodejs.org/) (v14 ou superior recomendado)
- [npm](https://www.npmjs.com/) ou [Yarn](https://yarnpkg.com/) (para executar o frontend React.js)

### Instalação

1. **Clone o repositório**:

    ```sh
    git clone https://github.com/bMoki/task-quarkus-react.git
    ```
2. **Navegue até o diretório do projeto**:
    ```sh
    cd task-quarkus-react
    ```
3. **Instale as dependências da API Quarkus**:
    ```sh
    cd api
    mvn clean install
    ```
4. **Instale as dependências do frontend React.js**:
    ```sh
    cd ../frontend
    npm install
    # or
    yarn install
    ```
### Executando a API Quarkus
Para iniciar a API REST Quarkus, execute os seguintes comandos a partir do diretório raiz do repositório:

```sh
cd api
mvn quarkus:dev
```

A API estará acessível em http://localhost:8080. Agora você pode fazer solicitações HTTP para testar os endpoints.

### Executando o Frontend React.js
Para iniciar o frontend React.js, execute os seguintes comandos a partir do diretório raiz do repositório:

```sh
cd frontend
npm run dev
# or
yarn dev
```

O frontend estará acessível em http://localhost:3000. Agora você pode abrir seu navegador e visualizar o site.

### Estrutura de Pastas
Estrutura de pastas da API Quarkus e do frontend React.js, destacando diretórios e arquivos importantes.

```java
task-quarkus-react/
  ├── api/
  │   ├── src/
  │   ├── pom.xml
  │   ├── ...
  ├── frontend/
  │   ├── src/
  │   │   ├── pages/
  │   │   ├── components/
  │   │   ├── styles/
  │   │   ├── ...
  │   ├── package.json
  │   ├── ...
  ├── README.md
```

### Contato
Se você tiver alguma dúvida ou precisar de assistência, sinta-se à vontade para entrar em contato em brenosonda.dev@gmail.com.