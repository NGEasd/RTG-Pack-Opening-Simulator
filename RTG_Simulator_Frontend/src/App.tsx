import { Route, Routes } from 'react-router-dom'
import GuestHomePage from './pages/HomePage'
import CardsPage from './pages/Cards'
import DetailedCardPage from './pages/DetailedCardPage'
import AddCardPage from './pages/AddCard'

import './App.css'
import PacksPage from './pages/PacksPage'
import OpenPackPage from './pages/OpenPackPage'
import AuthenticationPage from './pages/AuthenticationPage'
import { useContext, useEffect } from 'react'
import { AuthContext } from './global context/AuthContext'
import { getLoggedUser } from './services/userapi'
import InventoryPage from './pages/InventoryPage'

function App() {
  // get user always, if logged in
  const userContext = useContext(AuthContext);

  useEffect(() => {
    const setUser = async (token:string) => {
      const user = await getLoggedUser(token);
      if (user !== null) {
        userContext?.login(user);
      }
    }
    const token = localStorage.getItem('JWT');
    if (token) {
      console.log('Probal bejelentkezni, van token!')
      setUser(token);
    } else {
      console.log('Nem probal bejelentkezni, NINCS token!')
    }
    
  }, []);


  return (
    <>
      <main>
          <Routes>
            <Route path='/' element={<GuestHomePage />}></Route>
            <Route path='/home' element={<GuestHomePage />}></Route>
            <Route path='/cards' element={<CardsPage />}></Route>
            <Route path='/card/:id' element={<DetailedCardPage />}></Route>
            <Route path='/add-card' element={<AddCardPage />}></Route>
            <Route path='/packs' element={<PacksPage />}></Route>
            <Route path='/packs/:id' element={<OpenPackPage />}></Route>
            <Route path='/auth' element={<AuthenticationPage/>}></Route>
            <Route path='/inventory' element={<InventoryPage/>}></Route>
          </Routes>
      </main>
    </>
  )
}

export default App
