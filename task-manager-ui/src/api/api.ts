import axios from "axios";
import type {Task} from "../types/types.ts";

const API_URL = "http://localhost:8080/tasks";

export const getTasks = () => axios.get<Task[]>(API_URL);

export const getTask = (id: string) => axios.get<Task>(`${API_URL}/${id}`);

export const createTask = (title: string) =>
    axios.post<Task>(API_URL, { title });

export const markTaskDone = (id: string) =>
    getTask(id).then((task: { data: Task }) => {
        axios.put(`${API_URL}/${id}`,
            {
                ...task,
                status: "DONE"
            });
    });
