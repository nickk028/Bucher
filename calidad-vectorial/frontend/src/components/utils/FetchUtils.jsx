export const fetchData = async (url, metodos, data) => {
    if (metodos == "GET" || metodos == "DELETE") {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: metodos,
        credentials: "include",
        signal: controller.signal
    });
    } else if (metodos == "POST" || metodos == "PUT") {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: metodos,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
        credentials: "include",
        signal: controller.signal
    });        
    }
}