import gettext
import os
from tasks import TaskManager
import pathlib

LANGUAGES = ['en', 'es', 'ca']

def set_language(lang_code):
    """Configura el idioma del sistema."""
    if lang_code not in LANGUAGES:
        lang_code = 'en'

    localedir = os.path.join(os.path.abspath(os.path.dirname(__file__)), 'locale')
    lang = gettext.translation('messages', localedir=localedir, languages=[lang_code], fallback=True)
    lang.install()
    return lang.gettext


def select_language():
    print("Select language / Selecciona idioma / Selecciona llengua:")
    print("1. English")
    print("2. Español")
    print("3. Català")
    choice = input("> ")
    if choice == '2':
        return 'es'
    elif choice == '3':
        return 'ca'
    return 'en'


def main():
    lang = select_language()
    _ = set_language(lang)

    storage_path = pathlib.Path(__file__).parent / "storage.json"
    manager = TaskManager(filename=storage_path)

    print(_("Welcome to the Task Manager!"))

    while True:
        print("\n" + _("Choose an option:"))
        print("1.", _("Add a task"))
        print("2.", _("List tasks"))
        print("3.", _("Complete a task"))
        print("4.", _("Delete a task"))
        print("5.", _("Exit"))

        choice = input("> ")

        if choice == '1':
            title = input(_("Enter task title: "))
            manager.add_task(title)
            print(_("Task added successfully!"))

        elif choice == '2':
            tasks = manager.list_tasks()
            if not tasks:
                print(_("No tasks available."))
            else:
                print(_("Your tasks:"))
                for i, task in enumerate(tasks, 1):
                    status = _("Done") if task['done'] else _("Pending")
                    print(f"{i}. {task['title']} [{status}]")

        elif choice == '3':
            index = input(_("Enter the task number to mark as complete: "))
            if manager.complete_task(index):
                print(_("Task marked as complete!"))
            else:
                print(_("Invalid task number."))

        elif choice == '4':
            index = input(_("Enter the task number to delete: "))
            if manager.delete_task(index):
                print(_("Task deleted."))
            else:
                print(_("Invalid task number."))

        elif choice == '5':
            print(_("Goodbye!"))
            break
        else:
            print(_("Invalid option. Try again."))


if __name__ == "__main__":
    main()
