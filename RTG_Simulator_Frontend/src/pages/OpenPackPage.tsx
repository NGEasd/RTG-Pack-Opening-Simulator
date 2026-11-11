import { useParams } from "react-router-dom";
import NavigationBar from "../components/NavBar";
import OpeningScene from "../components/packs/OpeningScene";
import { UrlProp } from "../types/types";
import { useContext } from "react";
import { AuthContext } from "../global context/AuthContext";

function OpenPackPage() {
  const { id } = useParams();
  const user = useContext(AuthContext)?.user;
  if (id) {
    const urlID : UrlProp = {url:id};
    return (
      <>
        <NavigationBar />
        {(user !== null) ? <OpeningScene {...urlID} /> : <h2>You don`t have access to this page! Log in, to get items!</h2>}
      </>
    );
  }
}

export default OpenPackPage;