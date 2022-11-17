// import Header from './components/Header';
import NumberSelect from './components/NumberSelect';
import styled from 'styled-components';

const Container = styled.div`
  width: 40%;
`;

function App() {
  return (
    <Container>
      {/* <Header /> */}
      <NumberSelect />
    </Container>
  );
}

export default App;
