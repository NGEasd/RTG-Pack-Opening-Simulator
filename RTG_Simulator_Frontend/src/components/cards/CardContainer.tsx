import { useState, useEffect } from "react";
import { getFilteredCards } from "../../services/cardsapi";
import CardContainerElement, { CardContainerProp } from "./CardContainerElement";
import { Position, Rarity, SimpleCardProps } from "../../types/types";
import UpdateCardPopUp from "./UpdateCardPopUp";

function CardContainer() {

    // card data
    const [cards, setCards] = useState<SimpleCardProps[]>([]);

    // filter data
    const [minimOVR, setMinimOVR] = useState<number | ''>('');
    const [maxOVR, setMaxOVR] = useState<number | ''>('');
    const [selectedRarity, setSelectedRarity] = useState<Rarity | ''>('');
    const [selectedPosition, setSelectedPosition] = useState<Position | ''>('');
    const [sortByAcc, setSortByAcc] = useState<string>('overall-desc');

    // update card (if admin)
    const [updatePage, setUpdatePage] = useState(false);
    const [updatedCardId, setupdatedCardId] = useState<number | null>(null);

    const handleUpdateClick = (playerID: number) => {
        setupdatedCardId(playerID);
        setUpdatePage(true);
    };

    const handleClose = () => {
        setupdatedCardId(null);
        setUpdatePage(false);
    }

    useEffect(() => {

        // get the url parameters
        const params = new URLSearchParams();

        if (minimOVR !== '') params.append('minimOVR', String(minimOVR));
        if (maxOVR !== '') params.append('maxOVR', String(maxOVR));
        if (selectedRarity !== '') params.append('rarity', selectedRarity);
        if (selectedPosition !== '') params.append('position', selectedPosition);
        if (sortByAcc === 'overall-asc') {
            params.append('sortByAcc', 'true'); 
        } 
        else if (sortByAcc === 'overall-desc') {
            params.append('sortByAcc', 'false'); 
        }
        console.log("Fetching with params:", params.toString());

        const loadPlayers = async () => {
            try {
                const allCards = await getFilteredCards(params);
                setCards(allCards);
            }
            catch (error) {
                console.log("Internal error:", error);
            }
        }
        loadPlayers();
    }, [minimOVR, maxOVR, selectedRarity, selectedPosition, sortByAcc])

    return (
        <div className='card-discovery-page'>
            <h2>Cards - discover them!</h2>
                <div className="filter-sidebar">
                <h3>Filters</h3>

                <div className="filter-group">
                    <label htmlFor="minimOVR">Minimum OVR:</label>
                    <input
                    id="minimOVR"
                    type="number"
                    min="40"
                    max="99"
                    value={minimOVR}
                    onChange={(e) => setMinimOVR(e.target.value === '' ? '' : parseInt(e.target.value))}
                    placeholder="Min OVR"
                    />
                </div>

                <div className="filter-group">
                    <label htmlFor="maxOVR">Maximum OVR:</label>
                    <input
                    id="maxOVR"
                    type="number"
                    min="40"
                    max="99"
                    value={maxOVR}
                    onChange={(e) => setMaxOVR(e.target.value === '' ? '' : parseInt(e.target.value))}
                    placeholder="Max OVR"
                    />
                </div>

                <div className="filter-group">
                    <label htmlFor="rarity">Rarity:</label>
                    <select
                    id="rarity"
                    value={selectedRarity}
                    onChange={(e) => setSelectedRarity(e.target.value as Rarity | '')}
                    >
                    <option value="">All</option>
                    {Object.values(Rarity).map((r) => (
                        <option key={r} value={r}>{r}</option>
                    ))}
                    </select>
                </div>

                <div className="filter-group">
                    <label htmlFor="position">Position:</label>
                    <select
                    id="position"
                    value={selectedPosition}
                    onChange={(e) => setSelectedPosition(e.target.value as Position | '')}
                    >
                    <option value=''>All</option>
                    {Object.values(Position).map((p) => (
                        <option key={p} value={p}>{p}</option>
                    ))}
                    </select>
                </div>

                <div className="filter-group">
                    <label htmlFor="sortByAcc">Sort by:</label>
                    <select
                    id="sortByAcc"
                    value={sortByAcc}
                    onChange={(e) => setSortByAcc(e.target.value)}
                    >
                    <option value="overall-desc">OVR descending</option>
                    <option value="overall-asc">OVR accending</option>
                    </select>
                </div>
            </div>

            <div className='card-grid'> 
                {cards.map(card => {
                    const containerProps: CardContainerProp = {
                        card: card,
                        handler: handleUpdateClick,
                    }
                    return <CardContainerElement key={card.playerID} {...containerProps}/>
                })}
            </div>

            {updatePage && updatedCardId !== null && 
            (
                <UpdateCardPopUp cardID={updatedCardId} handleClose={handleClose} />
            )}
        </div>
        
    )
}

export default CardContainer;