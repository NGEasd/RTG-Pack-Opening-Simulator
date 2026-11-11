import { useContext, useEffect, useState } from "react";
import { getLoggedUser, getUserInventory, sellCard } from "../../services/userapi";
import { InventoryCardElementProps, SimpleCardProps } from "../../types/types";
import InventoryCardElement from "./InventoryCardElement";
import { AuthContext } from "../../global context/AuthContext";

function InventoryCardContainer() {

  const [cardsOwned, setCardsOwned] = useState<SimpleCardProps[]>([]);
  const [refreshInventoryTrigger, setRefreshInventoryTrigger] = useState(0); // after selling, we need to rerender the page

  const token = localStorage.getItem('JWT');
  const userContext = useContext(AuthContext);

  useEffect(() => {
    const getInventoryPlayers = async () => {
      if (token !== null) {
        const data:SimpleCardProps[] = await getUserInventory(token);
        setCardsOwned(data);
      }
    }

    getInventoryPlayers();
  }, [refreshInventoryTrigger, token])

  const manageCardSelling = async (id:number, price:number)  => {
    const sell = confirm(`Are you sure you want to sell this card for ${price} coins? This action cannot be undone.`)
    if (sell) {
      if (token) {
        await sellCard(id, token);
        const user = await getLoggedUser(token);
        if (user !== null) {
          userContext?.login(user);
        }
        setRefreshInventoryTrigger(prev => prev + 1);
      } else {
        console.log('No JWT Token!!');
      }
    }  
  }

  return (
    <>
      <h2>Inventory</h2>
      <div className='inventory-container'>
        {cardsOwned.length === 0 && (
          <h3>Inventory is empty. Open packs!</h3>
        )}

        {cardsOwned.map(card => {
          const prop: InventoryCardElementProps = {
            card:card,
            onSell: manageCardSelling,
          }

          return <InventoryCardElement key={card.playerID} {...prop}/>
        })}
      </div>
    </>
    
  );
}

export default InventoryCardContainer;