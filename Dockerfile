# Выбираем версию Maven и JDK
ARG MAVEN_VERSION=3.9
ARG JDK_VERSION=17

# Этап 1: Сборка
FROM maven:${MAVEN_VERSION}-eclipse-temurin-${JDK_VERSION} AS builder
WORKDIR /app
# Кэшируем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Копируем исходный код
COPY src ./src
# Собираем (тесты игнорируем)
RUN mvn clean package -DskipTests

# Этап 2: Запуск
FROM eclipse-temurin:${JDK_VERSION}-jre-alpine
WORKDIR /app
# Копируем JAR
COPY --from=builder /app/target/*.jar app.jar
# Открываем порт
EXPOSE 8080
# Запускаем с параметрами
ENTRYPOINT ["java", "-jar", "app.jar"]