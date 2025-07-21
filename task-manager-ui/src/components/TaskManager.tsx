import { useEffect, useState } from "react";
import type { Task } from "../types/types.ts";
import { createTask, getTasks, markTaskDone } from "../api/api.ts";

export const TaskManager = () => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [title, setTitle] = useState("");

    const loadTasks = async () => {
        const res = await getTasks();
        setTasks(res.data);
    };

    const handleAdd = async () => {
        if (!title.trim()) return;
        await createTask(title);
        setTitle("");
        await loadTasks();
    };

    const handleDone = async (task: Task) => {
        await markTaskDone(task.id);
        await loadTasks();
    };

    useEffect(() => {
        loadTasks();
    }, []);

    return (
        <div>
            <div>
                <input
                    placeholder="Task title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />
                <button onClick={handleAdd}>Add</button>
            </div>

            <ul>
                {tasks.map((task) => (
                    <li key={task.id} style={{ marginTop: 10 }}>
                        <b>{task.title}</b> - {task.status}
                        {task.status !== "DONE" && (
                            <button onClick={() => handleDone(task)} style={{ marginLeft: 10 }}>
                                Mark Done
                            </button>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    )};