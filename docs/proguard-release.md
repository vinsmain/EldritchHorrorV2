# R8 / ProGuard: памятка для release

## Почему debug и release ведут себя по-разному

В `debug` минификация выключена, а в `release` включён R8 (`minifyEnabled true`). R8 может переименовать или удалить класс, метод и конструктор, которые библиотека использует через reflection. Если код работает в debug, но падает или возвращает пустые данные только в release, сначала проверьте `app/proguard-rules.pro` и `app/build/outputs/mapping/release/mapping.txt`.

## Правила этого проекта

В правилах должны сохраняться следующие группы:

- `model.Game`, `model.ImageFile`, `model.Investigator` — Firebase Realtime Database читает JavaBean-свойства по именам;
- `api.json_model.**` — Retrofit/Gson создаёт и заполняет DTO через reflection;
- `strategy.**` — Moxy рефлексивно создаёт пользовательские `StateStrategy`, включая конструкторы;
- `**$$PresentersBinder`, `**$$PresentersFactory`, `**$$State`, `**$$ViewCommands` — кодогенерация Moxy;
- `database.oldDB.**` и ORMLite-правила — миграция прежней локальной БД использует аннотации и поля;
- `DaggerAppComponent` — приложение создаёт компонент напрямую.

Не отключайте R8 глобально и не добавляйте `-keep class ** { *; }`: это скроет дефект, увеличит APK и ухудшит обфускацию. Ограничивайте правило конкретным пакетом или типом, который действительно используется через reflection.

## Диагностика release-only сбоя

1. Получите чистый стек: `adb logcat -d -b crash -v threadtime`.
2. Сопоставьте обфусцированные классы с `app/build/outputs/mapping/release/mapping.txt` от **той же** сборки.
3. Найдите библиотеку, создающую объект или читающую свойства рефлексией.
4. Добавьте минимальное `-keep class <пакет> { *; }` либо сохраните нужные члены.
5. Соберите новый release и повторите сценарий на устройстве; debug-сборка не подтверждает корректность правил R8.

`app/release/mapping.txt` — рабочая копия mapping, полезная для расшифровки уже установленного APK. Не включайте её в исходники релиза; храните mapping каждого опубликованного AAB отдельно для Crashlytics и расследований.
