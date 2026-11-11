import { useContext } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import { AuthContext } from '../global context/AuthContext';
import { Role } from '../types/types';

function NavigationBar() {
    const userContext = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = async function() {
        
        localStorage.removeItem('JWT');
        userContext?.logout();
        navigate('/home');
    }

    return (
        <div className='nav-bar'>
            <div className='nav-brand'>
                <img src='/logo.svg' alt='RTG-logo' />
                <h2 className='brand-title'>RTG-Simulator</h2>
            </div>

            <div className='nav-links'>

                <Link to='/home'>
                    <div className='nav-element'>
                        <img src='/homeMenu.svg' alt='home' />
                        <p className='nav-menu-title'>Home</p>
                    </div>
                </Link>

                <Link to='/cards'>
                    <div className='nav-element'>
                        <img src='/cardsMenu.svg' alt='cards' />
                        <p className='nav-menu-title'>Cards</p>
                    </div>
                </Link>

                {userContext?.user === null && (
                    <Link to='/auth'>
                        <div className='nav-element'>
                            <img src='/authMenu.svg' alt='cards' />
                            <p className='nav-menu-title'>Authentication</p>
                        </div>
                    </Link>
                )}

                {(userContext?.user !== null && userContext?.user.role === Role.ADMIN) && (
                    <Link to='/add-card'>
                        <div className='nav-element'>
                            <img src='/addCard.svg' alt='addCards' />
                            <p className='nav-menu-title'>New card</p>
                        </div>
                    </Link>
                )}
                
                {(userContext?.user !== null && userContext?.user.role === Role.DEFAULT) && (
                    <Link to='/packs'>
                        <div className='nav-element'>
                            <img src='/packMenu.svg' alt='addCards' />
                            <p className='nav-menu-title'>Packs</p>
                        </div>
                    </Link>
                )}

                {userContext?.user !== null && (
                    <div className='nav-element' onClick={handleLogout}>
                        <img src='/authMenu.svg' alt='addCards' />
                        <p className='nav-menu-title'>Logout</p>
                    </div>
                )}
                
            </div>
        </div>
    )
}

export default NavigationBar;
