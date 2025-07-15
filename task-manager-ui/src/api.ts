import axios from "axios";
import type {Task} from "./types";

const API_URL = "http://localhost:8080/tasks";

export const getTasks = () => axios.get<Task[]>(API_URL);

export const getTask = (id: string) => axios.get<Task>(`${API_URL}/${id}`);

export const createTask = (title: string) =>
    axios.post<Task>(API_URL, { title });

export const markTaskDone = (id: string) =>
    getTask(id).then(task => {
        axios.put(`${API_URL}/${id}`,
            {
                title: task.data.title,
                description: task.data.description,
                status: "DONE"
            });
    });
