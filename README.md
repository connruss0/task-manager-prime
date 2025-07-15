# Task Manager UI
This repo contains code for the purposes of the Prime coding challenge

# How To Build, Run, and Access Frontend via Web Browser
1. Clone repo through the many ways of cloning a repo to local enviornment
2. Navigate to the the directory where you have cloned the repo via command line/terminal
3. Ensure that you have Docker Desktop installed
4. Run the following command to build the frontend image -> "docker build -t task-manager-ui ."
5. After building the image, run the following command to run the image -> "docker run -p 5173:80 task-manager-ui"
6. Make sure that the image is running successfully by visiting "http://localhost:5173/" in your prefered web browser
