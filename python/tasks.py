import json
import os

class TaskManager:
    def __init__(self, filename="storage.json"):
        self.filename = filename
        self.tasks = []
        self._load()

    def _load(self):
        if os.path.exists(self.filename):
            try:
                with open(self.filename, "r", encoding="utf-8") as f:
                    content = f.read().strip()
                    if content:
                        self.tasks = json.loads(content)
                    else:
                        self.tasks = []
            except json.JSONDecodeError:
                self.tasks = []
        else:
            self.tasks = []

    def _save(self):
        with open(self.filename, "w", encoding="utf-8") as f:
            json.dump(self.tasks, f, indent=2, ensure_ascii=False)

    def add_task(self, title):
        self.tasks.append({"title": title, "done": False})
        self._save()

    def list_tasks(self):
        return self.tasks

    def complete_task(self, index):
        try:
            i = int(index) - 1
            self.tasks[i]['done'] = True
            self._save()
            return True
        except (ValueError, IndexError):
            return False

    def delete_task(self, index):
        try:
            i = int(index) - 1
            del self.tasks[i]
            self._save()
            return True
        except (ValueError, IndexError):
            return False
