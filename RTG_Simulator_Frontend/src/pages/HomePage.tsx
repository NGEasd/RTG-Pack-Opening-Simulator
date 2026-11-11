import NavigationBar from "../components/NavBar";
import InfoCard from "../components/InfoCard";
import {Link} from 'react-router-dom';
import { useContext } from "react";
import { AuthContext } from "../global context/AuthContext";

function GuestHomePage() {
  const userContext = useContext(AuthContext);

  return (
    <>
      <div className="home-content">
        {(userContext?.user === null) && (
          <>
            <NavigationBar />

            <div className="left-side">
              <h1>Welcome to Road To Glory!</h1>
              <p>Please log in to start building your team and open packs.</p>
              <Link to='/auth'>
                <img className='login-poster' src='/loginPoster.jpg' alt='login poster'></img>
              </Link>
            </div>

            <div className="right-side">
              <InfoCard titleProp='Explore Cards' descriptProp='Discover new players to improve your team!' />
              <InfoCard titleProp='Build Your Team' descriptProp='Manage your squad and lead them to victory!' />
              <InfoCard titleProp='Open Packs' descriptProp='Unlock new players and strengthen your roster!' />
            </div>
          </>
        )}

        {(userContext?.user) && (
          <>
            <NavigationBar />
            <div className='user-balance-section'> 
              <h3>Balance:</h3>
              <div className='user-balance'>
                <img className='coin-image' src='coin.svg' alt='coin' />
                <p className='balance-value'>{userContext.user.balance}</p>
              </div>
            </div>

            <div className='welcome-section'> 
              <div className='left-side'>
                <h1>Welcome here, {userContext.user.username}!</h1>
                <p>Build your collection, to reach the top!</p>
              </div>
            </div>

            <div className='user-menu-section'> 
              <h2>Menu</h2>
              <div className='user-menu-links'> 
                <Link to='/packs'>
                  <div className='link-button'> 
                    Open packs
                  </div>
                </Link>
                <Link to='/cards'>
                  <div className='link-button'>
                    Discover cards
                  </div>
                </Link>
                <Link to='/inventory'>
                  <div className='link-button'>
                    Manage your inventory
                  </div>
                </Link>
              </div>
            </div>
          </>
        )}

      </div>
    </>
  );
}

export default GuestHomePage;

