import styled from 'styled-components';

const ArticlesGrid = styled.div`
  display: grid;
  grid-template-rows: repeat(4, 1fr);
  grid-template-columns: repeat(auto-fill, minmax(323px, 1fr));
  gap: 30px;
`;

export default ArticlesGrid;
