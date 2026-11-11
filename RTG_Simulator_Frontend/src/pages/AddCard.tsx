import { useContext } from "react";
import CardForm from "../components/cards/CardForm";
import NavigationBar from "../components/NavBar";
import { AuthContext } from "../global context/AuthContext";
import { Role } from "../types/types";

function AddCardPage() {
  const userRole = useContext(AuthContext)?.user?.role;
  return (
    <>
      <NavigationBar />
      {userRole === Role.ADMIN ? <CardForm /> : <h2>You don`t have access to this page!</h2>}
    </>
  );
}

export default AddCardPage;