import { useContext } from "react";
import InventoryCardContainer from "../components/cards/InventoryContainer";
import NavigationBar from "../components/NavBar";
import { AuthContext } from "../global context/AuthContext";

function InventoryPage() {
  const user = useContext(AuthContext)?.user;
  return (
      <>
        <NavigationBar />
        {(user !== null) ? <InventoryCardContainer /> : <h2>You don`t have access to this page! Log in, to get items!</h2>}
      </>
    );
}

export default InventoryPage;