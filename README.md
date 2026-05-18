# Sistema de Gerenciamento de Academia

Sistema web desenvolvido com Jakarta Faces (JSF) e PrimeFaces para gerenciamento de uma academia, incluindo cadastro de planos, alunos, personais e aulas.

## Tecnologias Utilizadas

- **Java** com Jakarta EE
- **JSF (Jakarta Faces)** — framework web
- **PrimeFaces 13** — componentes visuais
- **Template Manhattan** — layout do sistema
- **JPA / EclipseLink** — persistência de dados
- **PostgreSQL** — banco de dados
- **GlassFish 7** — servidor de aplicação
- **Maven** — gerenciamento de dependências

## Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)** com a seguinte estrutura de camadas:

```
entity/      → Representação das tabelas do banco (Model)
facade/      → Acesso ao banco de dados via JPA
controller/  → Lógica de negócio e integração com a view (Controller)
enumeration/ → Enumerações de valores fixos
filter/      → Filtro de segurança (autenticação)
webapp/      → Páginas XHTML com PrimeFaces (View)
```

## Funcionalidades (Casos de Uso)

### 1. Gerenciamento de Planos
Cadastro, edição, listagem e exclusão de planos da academia.
- Campos: nome, descrição, preço e duração em meses

### 2. Gerenciamento de Personais
Cadastro, edição, listagem e exclusão de personal trainers.
- Campos: nome, especialidade (Enum), data de nascimento, email e senha
- Especialidades disponíveis: Musculação, Pilates, Cardio, Crossfit, Yoga
- O personal também é o usuário do sistema — realiza login com email e senha

### 3. Gerenciamento de Alunos
Cadastro, edição, listagem e exclusão de alunos.
- Campos: nome, email, telefone, data de nascimento e plano contratado
- Cada aluno está vinculado a um plano

### 4. Gerenciamento de Aulas
Cadastro, edição, listagem e exclusão de aulas agendadas.
- Campos: tipo (Enum), horário, personal responsável e aluno participante
- Tipos de aula disponíveis: Pilates, Musculação, Crossfit, Yoga, Funcional
- Cada aula representa um agendamento individual entre um personal e um aluno

## Diagrama de Entidades e Relacionamentos

```
┌─────────────────┐          ┌─────────────────────┐
│      Plano      │          │       Personal      │
├─────────────────┤          ├─────────────────────┤
│ id (PK)         │          │ id (PK)             │
│ nome            │          │ nome                │
│ descricao       │          │ especialidade (Enum)│
│ preco           │          │ data_nascimento     │
│ duracao_meses   │          │ email               │
└────────┬────────┘          │ senha               │
         │ 1                 └──────────┬──────────┘
         │                              │ 1
         │ N                            │ 
┌────────▼────────┐                     │ N
│      Aluno      │            ┌────────▼──────────┐
├─────────────────┤            │       Aula        │
│ id (PK)         │ 1          ├───────────────────┤
│ nome            ├───────────►│ id (PK)           │
│ email           │ N          │ tipo (Enum)       │
│ telefone        │            │ horario           │
│ data_nascimento │            │ id_personal (FK)  │
│ id_plano (FK)   │            │ id_aluno (FK)     │
└─────────────────┘            └───────────────────┘
```

## Relacionamentos

| Relacionamento | Tipo | Descrição |
|---|---|---|
| Plano → Aluno | 1:N | Um plano pode ser contratado por vários alunos. Cada aluno tem exatamente um plano. |
| Personal → Aula | 1:N | Um personal ministra várias aulas. Cada aula pertence a um personal. |
| Aluno → Aula | 1:N | Um aluno participa de várias aulas. Cada aula pertence a um aluno específico. |

## Enumerações

### TipoAulaEnum
Define os tipos de aula disponíveis na academia:
- `PILATES`
- `MUSCULACAO`
- `CROSSFIT`
- `YOGA`
- `FUNCIONAL`

### EspecialidadeEnum
Define as especialidades disponíveis para personal trainers:
- `PILATES`
- `MUSCULACAO`
- `CARDIO`
- `CROSSFIT`
- `YOGA`

## Script do Banco de Dados

```sql
CREATE TABLE plano (
    id serial PRIMARY KEY,
    nome text NOT NULL,
    descricao text,
    preco numeric NOT NULL,
    duracao_meses integer NOT NULL
);

CREATE TABLE personal (
    id serial PRIMARY KEY,
    nome text NOT NULL,
    especialidade text NOT NULL,
    data_nascimento date NOT NULL,
    email text NOT NULL,
    senha text NOT NULL
);

-- Inserindo personal de teste para login
INSERT INTO personal (nome, especialidade, data_nascimento, email, senha)
VALUES ('Admin', 'MUSCULACAO', '1990-01-01', 'admin@academia.com', '123');

CREATE TABLE aluno (
    id serial PRIMARY KEY,
    nome text NOT NULL,
    email text NOT NULL,
    telefone text,
    data_nascimento date NOT NULL,
    id_plano integer NOT NULL,
    FOREIGN KEY (id_plano) REFERENCES plano(id)
);

CREATE TABLE aula (
    id serial PRIMARY KEY,
    tipo text NOT NULL,
    horario text NOT NULL,
    id_personal integer NOT NULL,
    id_aluno integer NOT NULL,
    FOREIGN KEY (id_personal) REFERENCES personal(id),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id)
);
```

## Autenticação e Segurança

O sistema utiliza a própria tabela `personal` para autenticação — não há uma tabela separada de usuários. Cada personal cadastrado com `email` e `senha` pode realizar login no sistema.

**Fluxo de autenticação:**
1. O personal acessa `login.xhtml` e informa email e senha
2. O `LoginController` consulta o banco via `PersonalFacade.buscarPorEmail()`
3. Se as credenciais forem válidas, o objeto do personal é salvo na sessão HTTP com o atributo `pessoaLogada`
4. O usuário é redirecionado para a área administrativa

**Filtro de segurança:**
O `FiltroAdministrativo` intercepta todas as requisições para `/admin/*`. Se não houver sessão válida, o usuário é redirecionado automaticamente para a tela de login.

## Como Executar

1. Clone o repositório
2. Crie o banco de dados PostgreSQL e execute o script SQL
3. Configure o Data Source no GlassFish apontando para o banco
4. Abra o projeto no NetBeans
5. Execute com F6 (GlassFish 7)
6. Acesse: `http://localhost:8080/NomeDoProjeto/faces/login.xhtml`