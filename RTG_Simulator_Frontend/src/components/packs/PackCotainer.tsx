import { useEffect, useState } from "react";
import { PackProps } from "../../types/types";
import { getAllPacks } from "../../services/packsapi";
import PackElement from "./PackElement";

function PackContainer() {
  const [packs, setPacks] = useState<PackProps[]>([]);

  useEffect(() => {
    const loadPacks = async () => {
      try {
        const token = localStorage.getItem('JWT');
        if (token !== null) {
          const allPacks = await getAllPacks(token);
          setPacks(allPacks);
        } else {
          console.log('NO JWT TOKEN!');
        }
        
      } catch(error) {
        console.log(error);
      }
    }
    loadPacks();
  }, [])

  return (
    <>
      <h2>Open packs, get players!</h2>
      <p>Choose a pack avabile for your buget</p>
      <div className="pack-container">
        {!packs || packs.length === 0 ? (
          <p>No packs available now. Please try again later!</p>
        ) : (
          packs.map(pack => (
            <PackElement key={pack.packID} {...pack} />
          ))
        )}
      </div>
    </>
  )
}

export default PackContainer;