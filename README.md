# task-manager-java
Console based tasks tracker
# Task Manager — Java

Консольный менеджер задач (аналог Trello/Jira в CLI).  
Язык: **Java (Core)** | Интерфейс: **CLI** | Данные: **in-memory (List + HashMap)**

---

## Структура проекта

```
src/
├── model/
│   ├── TaskStatus.java     # enum: TODO, IN_PROGRESS, DONE
│   ├── Priority.java       # enum: LOW, MEDIUM, HIGH
│   ├── Employee.java       # Сущность сотрудника
│   └── Task.java           # Сущность задачи
├── service/
│   └── TaskService.java    # Вся бизнес-логика и аналитика
└── main/
    └── Main.java           # Консольное меню (точка входа)
```

---

## Как запустить

### Через командную строку

```bash
# 1. Перейдите в папку src
cd src

# 2. Скомпилируйте все файлы
javac model/TaskStatus.java model/Priority.java model/Employee.java model/Task.java service/TaskService.java main/Main.java

# 3. Запустите
java main.Main
```

### Через IntelliJ IDEA

1. File → Open → выберите папку `task-manager`
2. Правой кнопкой на `src` → Mark Directory as → Sources Root
3. Откройте `main/Main.java` → нажмите Run

---

## Возможности

| Пункт меню | Описание |
|---|---|
| 1. Add employee | Добавить сотрудника (ID, имя, роль) |
| 2. Create task | Создать задачу (ID, название, описание, статус, приоритет, проект) |
| 3. Assign task | Назначить задачу сотруднику |
| 4. Update task status | Изменить статус задачи |
| 5. Tasks by employee | Все задачи конкретного сотрудника |
| 6. Tasks by status | Все задачи с заданным статусом |
| 7. Analytics | Аналитика (см. ниже) |
| 0. Exit | Выход |

### Подменю аналитики (пункт 7)

| Пункт | Описание |
|---|---|
| 1. Count by status | Количество задач по каждому статусу |
| 2. HIGH priority tasks | Список задач с высоким приоритетом |
| 3. Most overloaded employee | Сотрудник с наибольшим числом незавершённых задач |
| 4. Search by title | Поиск задачи по части названия |

---

## Валидация

- Нельзя добавить сотрудника или задачу с уже существующим ID
- Нельзя назначить задачу несуществующему сотруднику — выводится ошибка
- Статус и приоритет выбираются по номеру — невалидный ввод обрабатывается
- Некорректный ввод числа перехватывается через `NumberFormatException`
- Программа не падает ни при каком вводе пользователя
