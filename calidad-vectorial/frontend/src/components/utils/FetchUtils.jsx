export const getData = async (url, signal) => {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: "GET",
        credentials: "include",
        signal: signal
    });
    return respond;
}

export const postData = async (url, data, signal) => {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
        credentials: "include",
        signal: signal
    });          
    return respond;
}