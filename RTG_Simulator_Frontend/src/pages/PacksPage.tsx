import { useContext } from "react";
import NavigationBar from "../components/NavBar";
import PackContainer from "../components/packs/PackCotainer";
import { AuthContext } from "../global context/AuthContext";

function PacksPage() {
  const user = useContext(AuthContext)?.user;
  return (
    <>
      <NavigationBar />
      {(user !== null) ? <PackContainer /> : <h2>You don`t have access to this page! Log in, to get items!</h2>}
    </>
  );
}

export default PacksPage;