import { Navigate, Route, Routes } from 'react-router-dom'
import AppShell from './layouts/AppShell.jsx'
import PokedexPage from './pages/PokedexPage.jsx'
import TeamBuilderPage from './pages/TeamBuilderPage.jsx'
import TeamAnalysisPage from './pages/TeamAnalysisPage.jsx'

function App() {
  return (
    <Routes>
      <Route element={<AppShell />}>
        <Route index element={<Navigate to="/pokedex" replace />} />
        <Route path="/pokedex" element={<PokedexPage />} />
        <Route path="/team-builder" element={<TeamBuilderPage />} />
        <Route path="/team-analysis" element={<TeamAnalysisPage />} />
        <Route path="*" element={<Navigate to="/pokedex" replace />} />
      </Route>
    </Routes>
  )
}

export default App
