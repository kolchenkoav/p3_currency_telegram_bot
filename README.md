
# CryptoBot

CryptoBot — это Telegram-бот для отслеживания стоимости биткоина и управления подписками на уведомления.

## 📌 Функциональность

- Получение текущей стоимости биткоина по данным Binance.
- Подписка на уведомления при снижении цены до заданного значения.
- Управление подписками (получение информации, отмена).
- Логирование событий и ошибок.

## 🚀 Технологии

- **Java 17**
- **Spring Boot**
- **PostgreSQL**
- **Hibernate**
- **Telegram Bots API**
- **Binance API**
- **Lombok**
- **Testcontainers** (для тестирования)

## 📦 Установка и запуск

### 1. Клонируйте репозиторий
```sh
git clone https://github.com/your-repo/cryptobot.git
cd cryptobot
```

### 2. Укажите переменные окружения
Создайте `.env` файл и добавьте:
```env
BOT_NAME=your_bot_username
BOT_TOKEN=your_telegram_bot_token
DATABASE_URL=jdbc:postgresql://localhost:5432/db
DATABASE_USERNAME=root
DATABASE_PASSWORD=root
```

### 3. Запустите проект с помощью Gradle
```sh
./gradlew bootRun
```

## 📜 Доступные команды бота

| Команда            | Описание                                      |
|--------------------|----------------------------------------------|
| `/start`          | Запуск бота                                  |
| `/get_price`      | Получить текущую стоимость биткоина         |
| `/subscribe 50000` | Подписаться на уведомления при 50,000 USD   |
| `/get_subscription` | Узнать текущую подписку                    |
| `/unsubscribe`    | Отменить подписку                           |

## 🏗 Структура проекта

```plaintext
src/main/java/com/skillbox/cryptobot
├── bot/                  # Основная логика бота
│   ├── CryptoBot.java    # Основной класс бота
│   ├── command/          # Обработчики команд
├── service/              # Логика работы с криптовалютой и подписками
├── repository/           # Работа с базой данных
├── entity/               # Определение моделей данных
├── client/               # Взаимодействие с Binance API
├── configuration/        # Конфигурация Spring и бота
└── web/                  # REST API (UserController)
```

## 🧪 Тестирование

Для тестирования используется **JUnit 5** и **Testcontainers**:
```sh
./gradlew test
```

## 🛠 Развертывание

Для сборки `.jar` файла:
```sh
./gradlew build
```

Запуск `.jar`:
```sh
java -jar build/libs/cryptobot-0.0.1-SNAPSHOT.jar
```

## 🎯 TODO

- [ ] Добавить поддержку других криптовалют.
- [ ] Внедрить кэширование данных.
- [ ] Добавить веб-интерфейс для управления подписками.

## 📄 Лицензия

Проект распространяется под лицензией **MIT**.

---

💡 Разработано с ❤️ и Java.
```
