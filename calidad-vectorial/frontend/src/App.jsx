import { useState } from "react";
import Login from "./components/pages/login/Login";
import UserList from "./components/pages/templates/UserList"; // ejemplo de endpoint protegido

function App() {
  const [loggedIn, setLoggedIn] = useState(false);

  return (
    <div>
  <Login onLogin={() => setLoggedIn(true)} />
    {/* {loggedIn ? <UserList /> : <Login onLogin={() => setLoggedIn(true)} />} */}
  </div>
    );
}

export default App;