O SubTrack é um aplicativo Android desenvolvido em Kotlin com o objetivo de ajudar usuários a gerenciar suas assinaturas mensais de forma simples e organizada.

O app permite cadastrar, visualizar, editar e remover assinaturas, exibindo o nome do serviço e o valor associado a cada uma.

O projeto foi criado com foco em aprendizado prático, boas práticas de desenvolvimento Android e organização de código, 
servindo tanto como aplicação funcional quanto como base para futuras evoluções.

Este projeto utiliza uma API REST rodando localmente, portanto é necessário configurar o IP da sua máquina para que o aplicativo Android consiga se comunicar com o backend.

Após clonar o repositório, abra o terminal na pasta backend, instale as dependências e inicie o servidor usando node. 

Em seguida, descubra o IP local do seu computador e anote o endereço IPv4.

No Android Studio, substitua o IP nos arquivos RetrofitClient.kt  e network_security_config.xml, mantendo o protocolo http e a porta 3000.

Para que o celular consiga acessar a API, o Firewall do Windows precisa permitir conexões na porta 3000 (TCP). Caso contrário, o aplicativo apresentará erro de timeout.

O celular físico deve estar conectado ao mesmo Wi-Fi do computador.
