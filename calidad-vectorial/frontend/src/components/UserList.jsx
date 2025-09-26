import { useEffect, useState } from "react";

function UserList() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/usuario", {
      method: "GET",
      credentials: "include" // si JWT está en cookie
      // headers: { "Authorization": "Bearer " + token } // si usás localStorage
    })
      .then(res => res.json())
      .then(data => setUsers(data));
  }, []);

  return (
    <ul>
      {users.map(u => <li key={u.id}>{u.username}</li>)}
    </ul>
  );
}

export default UserList;