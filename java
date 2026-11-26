// ========================================
// Renilia Book - Application Logic
// ========================================

// --- Estado Global ---
const state = {
  user: null,
  books: [],
  currentView: 'home',
  searchQuery: '',
  selectedBook: null,
  readingProgress: {},
  toasts: []
};

// --- Dados dos Livros (Domínio Público - Project Gutenberg) ---
const BOOKS_DATA = [
  {
    id: 'dom-casmurro',
    title: 'Dom Casmurro',
    author: 'Machado de Assis',
    year: 1899,
    genre: ['Romance', 'Clássico Brasileiro', 'Literatura'],
    description: 'Obra-prima do realismo brasileiro, narrada em primeira pessoa por Bentinho, que relembra sua infância, o amor por Capitu e a dúvida cruel sobre a paternidade de seu filho. Um romance sobre ciúme, memória e a fragilidade da verdade.',
    cover: 'https://covers.openlibrary.org/b/id/8225286-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/56233.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/56233',
    pages: 256,
    language: 'pt',
    featured: true
  },
  {
    id: 'memorias-postumas',
    title: 'Memórias Póstumas de Brás Cubas',
    author: 'Machado de Assis',
    year: 1881,
    genre: ['Romance', 'Clássico Brasileiro', 'Satírico'],
    description: 'Narrado por um defunto autor, este romance inovador quebra convenções literárias com ironia e pessimismo. Brás Cubas reflete sobre sua vida medíocre, amores fracassados e a futilidade da existência.',
    cover: 'https://covers.openlibrary.org/b/id/8225290-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/56232.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/56232',
    pages: 224,
    language: 'pt',
    featured: false
  },
  {
    id: 'o-cortico',
    title: 'O Cortiço',
    author: 'Aluísio Azevedo',
    year: 1890,
    genre: ['Naturalismo', 'Clássico Brasileiro', 'Romance Social'],
    description: 'Retrato cruel da vida em um cortiço no Rio de Janeiro do século XIX. Através de João Romão e a mulata Rita Baiana, o romance expõe as condições desumanas, a promiscuidade e a luta pela sobrevivência.',
    cover: 'https://covers.openlibrary.org/b/id/8225300-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/56234.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/56234',
    pages: 312,
    language: 'pt',
    featured: false
  },
  {
    id: 'iracema',
    title: 'Iracema',
    author: 'José de Alencar',
    year: 1865,
    genre: ['Romance Indigenista', 'Clássico Brasileiro', 'Fundacional'],
    description: 'Lenda do Ceará que narra o amor entre a indígena Iracema e o português Martim Soares Moreno. Fundador mítico de Fortaleza, o romance mistura história, mito e poesia na formação da identidade brasileira.',
    cover: 'https://covers.openlibrary.org/b/id/8225310-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/56235.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/56235',
    pages: 192,
    language: 'pt',
    featured: false
  },
  {
    id: 'senhora',
    title: 'A Senhora',
    author: 'José de Alencar',
    year: 1875,
    genre: ['Romance Urbano', 'Clássico Brasileiro', 'Costumes'],
    description: 'Crítica à sociedade carioca do Segundo Reinado. Aurélia Camargo, moça rica e independente, casa-se com Fernando Seixas através de um contrato nupcial, expondo as relações mercantis por trás do casamento.',
    cover: 'https://covers.openlibrary.org/b/id/8225320-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/56236.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/56236',
    pages: 240,
    language: 'pt',
    featured: false
  },
  {
    id: 'orgulho-preconceito',
    title: 'Orgulho e Preconceito',
    author: 'Jane Austen',
    year: 1813,
    genre: ['Romance', 'Clássico Inglês', 'Sátira Social'],
    description: 'Elizabeth Bennet navega pelas convenções sociais, o orgulho de Mr. Darcy e as expectativas familiares na Inglaterra georgiana. Um dos romances mais amados da literatura mundial.',
    cover: 'https://covers.openlibrary.org/b/id/8225330-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/1342.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/1342',
    pages: 432,
    language: 'en',
    featured: false
  },
  {
    id: 'frankenstein',
    title: 'Frankenstein',
    author: 'Mary Shelley',
    year: 1818,
    genre: ['Ficção Científica', 'Gótico', 'Clássico'],
    description: 'Victor Frankenstein cria vida a partir da morte, mas abandona sua criatura. O monstro, rejeitado pela humanidade, busca vingança. O primeiro romance de ficção científica da história.',
    cover: 'https://covers.openlibrary.org/b/id/8225340-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/84.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/84',
    pages: 280,
    language: 'en',
    featured: false
  },
  {
    id: 'dracula',
    title: 'Drácula',
    author: 'Bram Stoker',
    year: 1897,
    genre: ['Terror', 'Gótico', 'Epistolar'],
    description: 'O conde Drácula viaja da Transilvânia para Londres em busca de novo sangue. Contado através de diários, cartas e recortes de jornal, definiu o vampiro na cultura popular.',
    cover: 'https://covers.openlibrary.org/b/id/8225350-L.jpg',
    downloadUrl: 'https://www.gutenberg.org/ebooks/345.epub.noimages',
    readUrl: 'https://www.gutenberg.org/ebooks/345',
    pages: 418,
    language: 'en',
    featured: false
  },
  {
    id: 'grande-sertao',
    title: 'Grande Sertão: Veredas',
    author: 'João Guimarães Rosa',
    year: 1956,
    genre: ['Modernismo', 'Clássico Brasileiro', 'Épico'],
    description: 'Riobaldo narra sua vida no sertão mineiro, entre jagunços, amores e a dúvida sobre a existência do Diabo. Linguagem inovadora, oralidade e filosofia em uma obra monumental.',
    cover: 'https://covers.openlibrary.org/b/id/8225360-L.jpg',
    downloadUrl: '#',
    readUrl: '#',
    pages: 600,
    language: 'pt',
    featured: false
  },
  {
    id: 'capitaes-areia',
    title: 'Capitães da Areia',
    author: 'Jorge Amado',
    year: 1937,
    genre: ['Romance Social', 'Modernismo', 'Clássico Brasileiro'],
    description: 'Gangue de meninos de rua em Salvador, liderada por Pedro Bala. Retrato comovente da marginalidade infantil, solidariedade e revolta contra a injustiça social na Bahia dos anos 30.',
    cover: 'https://covers.openlibrary.org/b/id/8225370-L.jpg',
    downloadUrl: '#',
    readUrl: '#',
    pages: 288,
    language: 'pt',
    featured: false
  },
  {
    id: 'vira-lata',
    title: 'Vidas Secas',
    author: 'Graciliano Ramos',
    year: 1938,
    genre: ['Romance Regionalista', 'Modernismo', 'Clássico Brasileiro'],
    description: 'Família de retirantes no sertão nordestino enfrenta a seca, a fome e a opressão. Linguagem seca e precisa reflete a aridez do ambiente e a dureza da existência.',
    cover: 'https://covers.openlibrary.org/b/id/8225380-L.jpg',
    downloadUrl: '#',
    readUrl: '#',
    pages: 176,
    language: 'pt',
    featured: false
  },
  {
    id: 'hora-estrela',
    title: 'A Hora da Estrela',
    author: 'Clarice Lispector',
    year: 1977,
    genre: ['Modernismo', 'Clássico Brasileiro', 'Existencialista'],
    description: 'Macabéa, datilógrafa nordestina no Rio, vive sua insignificância até consultar uma cartomante. Última obra de Clarice, metaficcional e comovente sobre a condição humana.',
    cover: 'https://covers.openlibrary.org/b/id/8225390-L.jpg',
    downloadUrl: '#',
    readUrl: '#',
    pages: 96,
    language: 'pt',
    featured: false
  }
];

// --- Utilitários ---
const $ = (sel, ctx = document) => ctx.querySelector(sel);
const $$ = (sel, ctx = document) => [...ctx.querySelectorAll(sel)];

const formatDate = (date) => new Intl.DateTimeFormat('pt-BR', { day: '2-digit', month: 'long', year: 'numeric' }).format(date);
const generateId = () => Date.now().toString(36) + Math.random().toString(36).substr(2);

// --- Persistência (localStorage) ---
const STORAGE_KEYS = {
  USER: 'renilia_user',
  BOOKS: 'renilia_books',
  PROGRESS: 'renilia_progress',
  FRIENDS: 'renilia_friends',
  CLUBS: 'renilia_clubs'
};

const loadState = () => {
  const user = localStorage.getItem(STORAGE_KEYS.USER);
  if (user) state.user = JSON.parse(user);
  
  const progress = localStorage.getItem(STORAGE_KEYS.PROGRESS);
  if (progress) state.readingProgress = JSON.parse(progress);
  
  // Mesclar livros salvos com padrão
  const savedBooks = localStorage.getItem(STORAGE_KEYS.BOOKS);
  if (savedBooks) {
    const parsed = JSON.parse(savedBooks);
    state.books = [...BOOKS_DATA, ...parsed.filter(b => !BOOKS_DATA.some(d => d.id === b.id))];
  } else {
    state.books = BOOKS_DATA;
  }
};

const saveState = () => {
  if (state.user) localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(state.user));
  localStorage.setItem(STORAGE_KEYS.PROGRESS, JSON.stringify(state.readingProgress));
};

// --- Toast System ---
const showToast = (message, type = 'info') => {
  const container = $('#toastContainer');
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `
    <span class="toast-icon" aria-hidden="true">${type === 'success' ? '✅' : type === 'error' ? '❌' : 'ℹ️'}</span>
    <span class="toast-message">${message}</span>
    <button class="toast-close" aria-label="Fechar">✕</button>
  `;
  toast.querySelector('.toast-close').onclick = () => toast.remove();
  container.appendChild(toast);
  setTimeout(() => toast.remove(), 5000);
};

// --- Modal System ---
const openModal = (html, className = '') => {
  const overlay = $('#modalOverlay');
  overlay.classList.remove('hidden');
  overlay.className = `modal-overlay ${className}`;
  overlay.innerHTML = `<div class="modal" role="document">${html}</div>`;
  overlay.querySelector('.modal-close')?.addEventListener('click', closeModal);
  overlay.addEventListener('click', e => { if (e.target === overlay) closeModal(); });
  document.body.style.overflow = 'hidden';
};

const closeModal = () => {
  $('#modalOverlay').classList.add('hidden');
  document.body.style.overflow = '';
};

// --- Renderização de Views ---
const renderView = (view) => {
  state.currentView = view;
  const main = $('#mainContent');
  
  // Atualizar nav ativo
  $$('.nav-btn, .mobile-nav-btn').forEach(btn => {
    btn.classList.toggle('active', btn.dataset.view === view);
  });
  
  // Fechar menu mobile
  $('#mobileMenuOverlay')?.classList.remove('open');
  
  switch (view) {
    case 'home': renderHome(main); break;
    case 'library': renderLibrary(main); break;
    case 'book-of-week': renderBookOfWeek(main); break;
    case 'friends': renderFriends(main); break;
    case 'clubs': renderClubs(main); break;
    case 'profile': renderProfile(main); break;
    case 'my-books': renderMyBooks(main); break;
    case 'auth': renderAuth(main); break;
    default: renderHome(main);
  }
  
  // Scroll to top
  window.scrollTo(0, 0);
};

const renderHome = (container) => {
  const featuredBook = state.books.find(b => b.featured) || state.books[0];
  const recentBooks = state.books.slice(0, 8);
  
  container.innerHTML = `
    <section class="hero" aria-labelledby="hero-title">
      <div class="container hero-content">
        <span class="hero-badge">📚 Biblioteca Virtual</span>
        <h1 id="hero-title" class="hero-title">Sua estante, suas histórias, seus amigos</h1>
        <p class="hero-subtitle">Descubra clássicos da literatura, acompanhe suas leituras, conecte-se com outros leitores e participe de clubes do livro. Tudo em um só lugar.</p>
        <div class="hero-actions">
          <button class="btn btn-primary btn-lg" data-view="library">📖 Explorar Biblioteca</button>
          <button class="btn btn-outline btn-lg" data-view="book-of-week">⭐ Ver Destaque da Semana</button>
        </div>
        <div class="hero-stats">
          <div class="stat-item"><div class="stat-value">${state.books.length}+</div><div class="stat-label">Livros Disponíveis</div></div>
          <div class="stat-item"><div class="stat-value">${state.user ? '1' : '0'}</div><div class="stat-label">Perfil Ativo</div></div>
          <div class="stat-item"><div class="stat-value">0</div><div class="stat-label">Amigos Conectados</div></div>
          <div class="stat-item"><div class="stat-value">0</div><div class="stat-label">Clubes Ativos</div></div>
        </div>
      </div>
    </section>
    
    <section class="section" aria-labelledby="featured-title">
      <div class="container">
        <header class="section-header">
          <h2 id="featured-title" class="section-title">⭐ Destaque da Semana</h2>
          <button class="btn btn-ghost" data-view="book-of-week">Ver detalhes →</button>
        </header>
        ${renderBookOfWeekCard(featuredBook, true)}
      </div>
    </section>
    
    <section class="section" aria-labelledby="library-title">
      <div class="container">
        <header class="section-header">
          <h2 id="library-title" class="section-title">📚 Clássicos Recentes</h2>
          <button class="btn btn-ghost" data-view="library">Ver todos →</button>
        </header>
        <div class="book-grid" role="list">
          ${recentBooks.map(book => renderBookCard(book)).join('')}
        </div>
      </div>
    </section>
    
    ${!state.user ? `
    <section class="section" style="background: var(--color-parchment); border-radius: var(--radius-xl); margin: 0 var(--space-4) var(--space-12);">
      <div class="container" style="text-align: center; padding: var(--space-12) var(--space-4);">
        <h2 class="section-title" style="margin-bottom: var(--space-4);">👋 Bem-vindo ao Renilia Book!</h2>
        <p style="color: var(--color-brown); margin-bottom: var(--space-6); max-width: 500px; margin-left: auto; margin-right: auto;">Crie sua conta gratuita para salvar livros, acompanhar progresso de leitura, fazer amigos e participar de clubes do livro.</p>
        <button class="btn btn-primary btn-lg" data-view="auth">Criar Conta Grátis</button>
      </div>
    </section>
    ` : ''}
  `;
  
  // Delegação de eventos para botões data-view
  container.querySelectorAll('[data-view]').forEach(btn => {
    btn.addEventListener('click', () => renderView(btn.dataset.view));
  });
};

const renderBookOfWeek = (container) => {
  const book = state.books.find(b => b.featured) || state.books[0];
  container.innerHTML = `
    <div class="container">
      <article class="book-of-week" aria-labelledby="bow-title">
        <div class="bow-content">
          <img src="${book.cover}" alt="" class="bow-cover" loading="lazy">
          <div>
            <span class="bow-badge">Destaque da Semana</span>
            <h1 id="bow-title" class="bow-title">${book.title}</h1>
            <p class="bow-author">por ${book.author}</p>
            <p class="bow-description">${book.description}</p>
            <div class="bow-meta">
              <span>📅 ${book.year}</span>
              <span>📄 ${book.pages} páginas</span>
              <span>🌐 ${book.language === 'pt' ? 'Português' : 'Inglês'}</span>
              ${book.genre.map(g => `<span class="card-tag">${g}</span>`).join('')}
            </div>
            <div class="bow-actions">
              ${book.downloadUrl !== '#' ? `<a href="${book.downloadUrl}" target="_blank" rel="noopener" class="btn btn-primary">⬇️ Baixar (ePub)</a>` : '<button class="btn btn-primary" disabled>⬇️ Indisponível</button>'}
              ${book.readUrl !== '#' ? `<a href="${book.readUrl}" target="_blank" rel="noopener" class="btn btn-outline">📖 Ler Online</a>` : ''}
              <button class="btn btn-secondary" onclick="addToMyBooks('${book.id}')">➕ Adicionar à Estante</button>
            </div>
          </div>
        </div>
      </article>
    </div>
  `;
};

const renderBookOfWeekCard = (book, compact = false) => {
  if (!book) return '<div class="empty-state"><div class="empty-icon">📭</div><h3 class="empty-title">Nenhum destaque</h3><p class="empty-description">Nenhum livro selecionado como destaque da semana.</p></div>';
  
  return `
    <article class="book-of-week" style="${compact ? 'max-width: 600px; margin: 0 auto;' : ''}">
      <div class="bow-content" style="${compact ? 'grid-template-columns: 140px 1fr; padding: var(--space-6);' : ''}">
        <img src="${book.cover}" alt="" class="bow-cover" style="${compact ? 'max-width: 140px; box-shadow: var(--shadow-md);' : ''}" loading="lazy">
        <div>
          <span class="bow-badge">Destaque da Semana</span>
          <h3 class="bow-title" style="${compact ? 'font-size: var(--text-xl);' : ''}">${book.title}</h3>
          <p class="bow-author" style="${compact ? 'font-size: var(--text-base);' : ''}">por ${book.author}</p>
          <p class="bow-description" style="${compact ? 'font-size: var(--text-sm); margin-bottom: var(--space-4); display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden;' : ''}">${book.description}</p>
          <div class="bow-meta" style="${compact ? 'font-size: var(--text-xs);' : ''}">
            <span>📅 ${book.year}</span>
            <span>📄 ${book.pages} páginas</span>
            ${book.genre.slice(0, 2).map(g => `<span class="card-tag">${g}</span>`).join('')}
          </div>
          <div class="bow-actions" style="${compact ? 'gap: var(--space-2);' : ''}">
            <button class="btn btn-primary ${compact ? 'btn-sm' : ''}" onclick="openBookDetail('${book.id}')">Ver Detalhes</button>
            ${book.downloadUrl !== '#' ? `<a href="${book.downloadUrl}" target="_blank" rel="noopener" class="btn btn-outline ${compact ? 'btn-sm' : ''}">Baixar</a>` : ''}
          </div>
        </div>
      </div>
    </article>
  `;
};

const renderLibrary = (container) => {
  const genres = [...new Set(state.books.flatMap(b => b.genre))].sort();
  const filteredBooks = state.searchQuery 
    ? state.books.filter(b => 
        b.title.toLowerCase().includes(state.searchQuery.toLowerCase()) ||
        b.author.toLowerCase().includes(state.searchQuery.toLowerCase()) ||
        b.genre.some(g => g.toLowerCase().includes(state.searchQuery.toLowerCase()))
      )
    : state.books;
  
  container.innerHTML = `
    <div class="container">
      <header class="section-header">
        <h2 class="section-title">📖 Biblioteca Completa</h2>
        <div style="display: flex; gap: var(--space-3); flex-wrap: wrap;">
          <select id="genreFilter" class="form-select" style="width: auto; min-width: 180px; padding: var(--space-2) var(--space-3);">
            <option value="">Todos os gêneros</option>
            ${genres.map(g => `<option value="${g}">${g}</option>`).join('')}
          </select>
          <select id="langFilter" class="form-select" style="width: auto; min-width: 150px; padding: var(--space-2) var(--space-3);">
            <option value="">Todos os idiomas</option>
            <option value="pt">Português</option>
            <option value="en">Inglês</option>
          </select>
        </div>
      </header>
      
      ${filteredBooks.length === 0 ? `
        <div class="empty-state">
          <div class="empty-icon">🔍</div>
          <h3 class="empty-title">Nenhum livro encontrado</h3>
          <p class="empty-description">Tente ajustar sua busca ou filtros.</p>
        </div>
      ` : `
        <div class="book-grid" role="list" id="bookGrid">
          ${filteredBooks.map(book => renderBookCard(book)).join('')}
        </div>
      `}
    </div>
  `;
  
  $('#genreFilter')?.addEventListener('change', e => {
    const genre = e.target.value;
    const lang = $('#langFilter').value;
    let filtered = state.books;
    if (genre) filtered = filtered.filter(b => b.genre.includes(genre));
    if (lang) filtered = filtered.filter(b => b.language === lang);
    $('#bookGrid').innerHTML = filtered.map(renderBookCard).join('');
    attachBookCardEvents();
  });
  
  $('#langFilter')?.addEventListener('change', e => {
    const lang = e.target.value;
    const genre = $('#genreFilter').value;
    let filtered = state.books;
    if (genre) filtered = filtered.filter(b => b.genre.includes(genre));
    if (lang) filtered = filtered.filter(b => b.language === lang);
    $('#bookGrid').innerHTML = filtered.map(renderBookCard).join('');
    attachBookCardEvents();
  });
};

const renderBookCard = (book) => {
  const inMyBooks = state.user && state.readingProgress[book.id];
  const progress = inMyBooks ? state.readingProgress[book.id] : 0;
  
  return `
    <article class="book-card" role="listitem" tabindex="0" data-book-id="${book.id}" aria-label="${book.title} por ${book.author}">
      <div class="book-cover-wrapper">
        <img src="${book.cover}" alt="" class="book-cover" loading="lazy">
        <div class="book-overlay">
          <button class="btn-read" onclick="event.stopPropagation(); openBookDetail('${book.id}')">Ler/Ver</button>
        </div>
        ${book.featured ? '<span style="position:absolute;top:var(--space-3);left:var(--space-3);background:var(--color-gold);color:var(--color-walnut);padding:var(--space-1) var(--space-2);border-radius:var(--radius-full);font-size:var(--text-xs);font-weight:700;">⭐ Destaque</span>' : ''}
      </div>
      <div class="book-info">
        <h3 class="book-title">${book.title}</h3>
        <p class="book-author">${book.author}</p>
        <div class="book-tags">
          ${book.genre.slice(0, 3).map(g => `<span class="book-tag">${g}</span>`).join('')}
          ${book.genre.length > 3 ? `<span class="book-tag">+${book.genre.length - 3}</span>` : ''}
        </div>
        ${inMyBooks ? `
          <div style="margin-top: var(--space-3);">
            <div class="reading-progress" style="height: 4px;">
              <div class="reading-progress-bar" style="width: ${progress}%"></div>
            </div>
            <span style="font-size: var(--text-xs); color: var(--color-text-light);">${progress}% lido</span>
          </div>
        ` : ''}
      </div>
    </article>
  `;
};

const attachBookCardEvents = () => {
  $$('.book-card').forEach(card => {
    card.addEventListener('click', () => openBookDetail(card.dataset.bookId));
    card.addEventListener('keydown', e => { if (e.key === 'Enter' || e.key === ' ') openBookDetail(card.dataset.bookId); });
  });
};

const renderFriends = (container) => {
  container.innerHTML = `
    <div class="container">
      <header class="section-header">
        <h2 class="section-title">👥 Amigos</h2>
        ${state.user ? '<button class="btn btn-primary btn-sm" onclick="openAddFriendModal()">➕ Adicionar Amigo</button>' : ''}
      </header>
      
      <div class="empty-state">
        <div class="empty-icon">👥</div>
        <h3 class="empty-title">Sem amigos ainda</h3>
        <p class="empty-description">Conecte-se com outros leitores, compare progresso de leitura, veja o que seus amigos estão lendo e troque recomendações.</p>
        ${state.user ? '<button class="btn btn-primary mt-4" onclick="openAddFriendModal()">Adicionar Primeiro Amigo</button>' : '<p class="mt-4" style="color: var(--color-text-light);">Faça login para adicionar amigos</p>'}
      </div>
      
      ${state.user ? `
      <div style="margin-top: var(--space-12); padding: var(--space-6); background: var(--color-parchment); border-radius: var(--radius-lg); border: 1px dashed var(--color-border);">
        <h4 style="font-family: var(--font-display); color: var(--color-ink); margin-bottom: var(--space-3);">💡 Em desenvolvimento</h4>
        <p style="color: var(--color-brown); font-size: var(--text-sm);">Funcionalidades planejadas: busca de usuários, convites por código/email, comparativo de estantes, desafios de leitura em grupo, recomendações personalizadas.</p>
      </div>
      ` : ''}
    </div>
  `;
};

const renderClubs = (container) => {
  container.innerHTML = `
    <div class="container">
      <header class="section-header">
        <h2 class="section-title">🏛️ Clubes do Livro</h2>
        ${state.user ? '<button class="btn btn-primary btn-sm" onclick="openCreateClubModal()">➕ Criar Clube</button>' : ''}
      </header>
      
      <div class="empty-state">
        <div class="empty-icon">🏛️</div>
        <h3 class="empty-title">Sem clubes ainda</h3>
        <p class="empty-description">Participe ou crie clubes de leitura para discutir livros em grupo, definir metas de leitura compartilhadas e trocar ideias com outros leitores apaixonados.</p>
        ${state.user ? '<button class="btn btn-primary mt-4" onclick="openCreateClubModal()">Criar Primeiro Clube</button>' : '<p class="mt-4" style="color: var(--color-text-light);">Faça login para criar ou entrar em clubes</p>'}
      </div>
      
      ${state.user ? `
      <div style="margin-top: var(--space-12); padding: var(--space-6); background: var(--color-parchment); border-radius: var(--radius-lg); border: 1px dashed var(--color-border);">
        <h4 style="font-family: var(--font-display); color: var(--color-ink); margin-bottom: var(--space-3);">💡 Em desenvolvimento</h4>
        <p style="color: var(--color-brown); font-size: var(--text-sm);">Funcionalidades planejadas: clubes públicos/privados, votação de livro do mês, fórum de discussão por capítulo, calendário de encontros, estatísticas do grupo.</p>
      </div>
      ` : ''}
    </div>
  `;
};

const renderProfile = (container) => {
  if (!state.user) { renderAuth(container); return; }
  
  const user = state.user;
  const myBooksIds = Object.keys(state.readingProgress);
  const myBooks = state.books.filter(b => myBooksIds.includes(b.id));
  const totalPages = myBooks.reduce((sum, b) => sum + b.pages, 0);
  const booksRead = myBooks.filter(b => state.readingProgress[b.id] >= 100).length;
  const readingNow = myBooks.filter(b => state.readingProgress[b.id] > 0 && state.readingProgress[b.id] < 100).length;
  
  container.innerHTML = `
    <div class="container">
      <header class="profile-header">
        <div class="profile-avatar" aria-hidden="true">${user.name.charAt(0).toUpperCase()}</div>
        <h1 class="profile-name">${user.name}</h1>
        <p class="profile-bio">${user.bio || 'Leitor apaixonado por boas histórias. Adicione uma bio nas configurações!'}</p>
        <div class="profile-stats">
          <div class="profile-stat"><div class="profile-stat-value">${myBooks.length}</div><div class="profile-stat-label">Na Estante</div></div>
          <div class="profile-stat"><div class="profile-stat-value">${booksRead}</div><div class="profile-stat-label">Lidos</div></div>
          <div class="profile-stat"><div class="profile-stat-value">${readingNow}</div><div class="profile-stat-label">Lendo</div></div>
          <div class="profile-stat"><div class="profile-stat-value">${totalPages}</div><div class="profile-stat-label">Páginas</div></div>
        </div>
      </header>
      
      <div class="profile-tabs" role="tablist">
        <button class="profile-tab active" role="tab" aria-selected="true" data-tab="reading">📖 Lendo Agora</button>
        <button class="profile-tab" role="tab" aria-selected="false" data-tab="want">📋 Quero Ler</button>
        <button class="profile-tab" role="tab" aria-selected="false" data-tab="read">✅ Lidos</button>
        <button class="profile-tab" role="tab" aria-selected="false" data-tab="settings">⚙️ Configurações</button>
      </div>
      
      <div class="profile-section active" id="tab-reading" role="tabpanel">
        ${renderReadingList(myBooks.filter(b => state.readingProgress[b.id] > 0 && state.readingProgress[b.id] < 100), 'reading')}
      </div>
      <div class="profile-section" id="tab-want" role="tabpanel">
        ${renderReadingList(myBooks.filter(b => !state.readingProgress[b.id] || state.readingProgress[b.id] === 0), 'want')}
      </div>
      <div class="profile-section" id="tab-read" role="tabpanel">
        ${renderReadingList(myBooks.filter(b => state.readingProgress[b.id] >= 100), 'read')}
      </div>
      <div class="profile-section" id="tab-settings" role="tabpanel">
        ${renderSettings()}
      </div>
    </div>
  `;
  
  // Tab switching
  $$('.profile-tab').forEach(tab => {
    tab.addEventListener('click', () => {
      $$('.profile-tab').forEach(t => { t.classList.remove('active'); t.setAttribute('aria-selected', 'false'); });
      $$('.profile-section').forEach(s => s.classList.remove('active'));
      tab.classList.add('active');
      tab.setAttribute('aria-selected', 'true');
      $(`#tab-${tab.dataset.tab}`).classList.add('active');
    });
  });
  
  // Progress bars
  $$('.reading-progress-bar').forEach(bar => {
    const width = bar.style.width;
    bar.style.width = '0%';
    requestAnimationFrame(() => { bar.style.width = width; });
  });
};

const renderReadingList = (books, type) => {
  if (books.length === 0) {
    const messages = {
      reading: { icon: '📖', title: 'Nada em andamento', desc: 'Adicione livros à sua estante e comece a ler!' },
      want: { icon: '📋', title: 'Lista vazia', desc: 'Salve livros que deseja ler no futuro.' },
      read: { icon: '✅', title: 'Nenhum livro finalizado', desc: 'Seus livros concluídos aparecerão aqui.' }
    };
    const m = messages[type];
    return `<div class="empty-state"><div class="empty-icon">${m.icon}</div><h3 class="empty-title">${m.title}</h3><p class="empty-description">${m.desc}</p></div>`;
  }
  
  return books.map(book => {
    const progress = state.readingProgress[book.id] || 0;
    return `
      <article class="reading-card" data-book-id="${book.id}">
        <img src="${book.cover}" alt="" class="reading-cover" loading="lazy">
        <div class="reading-info">
          <h3 class="reading-title">${book.title}</h3>
          <p class="reading-author">${book.author}</p>
          <div class="reading-progress">
            <div class="reading-progress-bar" style="width: ${progress}%"></div>
          </div>
          <div class="reading-meta">
            <span>${progress}% concluído</span>
            <span>${book.pages} páginas</span>
            <button class="btn btn-ghost btn-sm" onclick="event.stopPropagation(); openBookDetail('${book.id}')">Ver detalhes</button>
            <button class="btn btn-ghost btn-sm" onclick="event.stopPropagation(); updateProgress('${book.id}')">${progress >= 100 ? '↩️ Relere' : '📖 Atualizar'}</button>
          </div>
        </div>
      </article>
    `;
  }).join('');
};

const renderSettings = () => {
  const user = state.user;
  return `
    <form id="settingsForm" class="auth-form" style="max-width: 500px;">
      <h3 style="font-family: var(--font-display); font-size: var(--text-xl); margin-bottom: var(--space-6); color: var(--color-ink);">⚙️ Configurações do Perfil</h3>
      
      <div class="form-group">
        <label class="form-label" for="setName">Nome</label>
        <input type="text" id="setName" class="form-input" value="${user.name}" required>
      </div>
      
      <div class="form-group">
        <label class="form-label" for="setEmail">Email</label>
        <input type="email" id="setEmail" class="form-input" value="${user.email}" required>
      </div>
      
      <div class="form-group">
        <label class="form-label" for="setBio">Bio</label>
        <textarea id="setBio" class="form-input form-textarea" rows="4" placeholder="Conte um pouco sobre você...">${user.bio || ''}</textarea>
      </div>
      
      <div class="form-group">
        <label class="form-label" for="setAvatar">Avatar (emoji ou inicial)</label>
        <input type="text" id="setAvatar" class="form-input" value="${user.avatar || user.name.charAt(0).toUpperCase()}" maxlength="2" placeholder="📚">
        <p class="form-hint">Use um emoji ou uma letra inicial</p>
      </div>
      
      <button type="submit" class="btn btn-primary btn-full">Salvar Alterações</button>
      
      <hr style="margin: var(--space-8) 0; border-color: var(--color-border-light);">
      
      <button type="button" class="btn btn-outline btn-full" style="color: var(--color-error); border-color: var(--color-error);" onclick="logout()">🚪 Sair da Conta</button>
    </form>
  `;
};

const renderMyBooks = (container) => {
  if (!state.user) { renderAuth(container); return; }
  renderProfile(container); // Mesmo conteúdo, mas foca na aba "Lendo Agora"
  // Forçar aba "Lendo Agora"
  setTimeout(() => {
    $$('.profile-tab').forEach(t => t.classList.remove('active'));
    $$('.profile-section').forEach(s => s.classList.remove('active'));
    $('.profile-tab[data-tab="reading"]')?.classList.add('active');
    $('#tab-reading')?.classList.add('active');
  }, 0);
};

const renderAuth = (container) => {
  container.innerHTML = `
    <div class="auth-page">
      <div class="auth-card" id="authCard">
        <header class="auth-header">
          <h1 class="auth-title">Bem-vindo ao Renilia Book</h1>
          <p class="auth-subtitle">Crie sua conta ou entre para acessar sua biblioteca pessoal</p>
        </header>
        
        <form id="loginForm" class="auth-form">
          <div class="form-group">
            <label class="form-label" for="loginEmail">Email</label>
            <input type="email" id="loginEmail" class="form-input" placeholder="seu@email.com" required autocomplete="email">
          </div>
          <div class="form-group">
            <label class="form-label" for="loginPassword">Senha</label>
            <input type="password" id="loginPassword" class="form-input" placeholder="••••••••" required autocomplete="current-password">
          </div>
          <button type="submit" class="btn btn-primary btn-full btn-lg">Entrar</button>
        </form>
        
        <div class="auth-divider">ou</div>
        
        <form id="registerForm" class="auth-form hidden">
          <div class="form-group">
            <label class="form-label" for="regName">Nome</label>
            <input type="text" id="regName" class="form-input" placeholder="Seu nome" required autocomplete="name">
          </div>
          <div class="form-group">
            <label class="form-label" for="regEmail">Email</label>
            <input type="email" id="regEmail" class="form-input" placeholder="seu@email.com" required autocomplete="email">
          </div>
          <div class="form-group">
            <label class="form-label" for="regPassword">Senha</label>
            <input type="password" id="regPassword" class="form-input" placeholder="Mínimo 6 caracteres" required minlength="6" autocomplete="new-password">
          </div>
          <div class="form-group">
            <label class="form-label" for="regConfirm">Confirmar Senha</label>
            <input type="password" id="regConfirm" class="form-input" placeholder="Repita a senha" required autocomplete="new-password">
          </div>
          <div class="form-group checkbox-group">
            <input type="checkbox" id="regTerms" class="checkbox-input" required>
            <span class="checkbox-custom"></span>
            <label class="checkbox-label" for="regTerms">Aceito os <a href="#">Termos de Uso</a> e <a href="#">Política de Privacidade</a></label>
          </div>
          <button type="submit" class="btn btn-primary btn-full btn-lg">Criar Conta</button>
        </form>
        
        <p class="auth-footer">
          <span id="authSwitch">Não tem conta? <a href="#" id="showRegister">Cadastre-se</a></span>
          <span id="authSwitchBack" class="hidden">Já tem conta? <a href="#" id="showLogin">Entrar</a></span>
        </p>
      </div>
    </div>
  `;
  
  const loginForm = $('#loginForm');
  const registerForm = $('#registerForm');
  const showRegister = $('#showRegister');
  const showLogin = $('#showLogin');
  const authSwitch = $('#authSwitch');
  const authSwitchBack = $('#authSwitchBack');
  
  showRegister?.addEventListener('click', e => {
    e.preventDefault();
    loginForm.classList.add('hidden');
    registerForm.classList.remove('hidden');
    authSwitch.classList.add('hidden');
    authSwitchBack.classList.remove('hidden');
  });
  
  showLogin?.addEventListener('click', e => {
    e.preventDefault();
    registerForm.classList.add('hidden');
    loginForm.classList.remove('hidden');
    authSwitchBack.classList.add('hidden');
    authSwitch.classList.remove('hidden');
  });
  
  loginForm?.addEventListener('submit', handleLogin);
  registerForm?.addEventListener('submit', handleRegister);
};

// --- Handlers de Autenticação ---
const handleLogin = async (e) => {
  e.preventDefault();
  const email = $('#loginEmail').value.trim().toLowerCase();
  const password = $('#loginPassword').value;
  
  // Simulação - em produção usar backend real
  const users = JSON.parse(localStorage.getItem('renilia_users') || '{}');
  const user = users[email];
  
  if (user && user.password === password) { // Em produção: hash + salt
    state.user = { id: email, name: user.name, email: user.email, bio: user.bio || '', avatar: user.avatar || '', createdAt: user.createdAt };
    saveState();
    updateUserMenu();
    showToast('Bem-vindo de volta, ' + user.name + '!', 'success');
    renderView('home');
  } else {
    showToast('Email ou senha incorretos', 'error');
  }
};

const handleRegister = async (e) => {
  e.preventDefault();
  const name = $('#regName').value.trim();
  const email = $('#regEmail').value.trim().toLowerCase();
  const password = $('#regPassword').value;
  const confirm = $('#regConfirm').value;
  
  if (password !== confirm) { showToast('As senhas não coincidem', 'error'); return; }
  if (password.length < 6) { showToast('A senha deve ter pelo menos 6 caracteres', 'error'); return; }
  
  const users = JSON.parse(localStorage.getItem('renilia_users') || '{}');
  if (users[email]) { showToast('Este email já está cadastrado', 'error'); return; }
  
  users[email] = { name, email, password, createdAt: new Date().toISOString() };
  localStorage.setItem('renilia_users', JSON.stringify(users));
  
  state.user = { id: email, name, email, bio: '', avatar: '', createdAt: new Date().toISOString() };
  saveState();
  updateUserMenu();
  showToast('Conta criada com sucesso! Bem-vindo, ' + name + '!', 'success');
  renderView('home');
};

const logout = () => {
  state.user = null;
  localStorage.removeItem(STORAGE_KEYS.USER);
  updateUserMenu();
  showToast('Você saiu da conta', 'info');
  renderView('home');
};

const updateUserMenu = () => {
  const loginBtn = $('#loginBtn');
  const dropdown = $('#userDropdown');
  
  if (state.user) {
    loginBtn.innerHTML = `<span aria-hidden="true">${state.user.avatar || state.user.name.charAt(0).toUpperCase()}</span><span>${state.user.name}</span>`;
    loginBtn.onclick = (e) => { e.stopPropagation(); dropdown.classList.toggle('hidden'); };
  } else {
    loginBtn.innerHTML = `<span aria-hidden="true">👤</span><span>Entrar</span>`;
    loginBtn.onclick = () => renderView('auth');
    dropdown.classList.add('hidden');
  }
};

// --- Book Detail Modal ---
window.openBookDetail = (bookId) => {
  const book = state.books.find(b => b.id === bookId);
  if (!book) return;
  
  const inMyBooks = state.user && state.readingProgress[bookId];
  const progress = inMyBooks ? state.readingProgress[bookId] : 0;
  
  openModal(`
    <header class="modal-header">
      <h2 class="modal-title" id="modalTitle">${book.title}</h2>
      <button class="modal-close" aria-label="Fechar">✕</button>
    </header>
    <div class="modal-body">
      <div class="book-detail-content">
        <img src="${book.cover}" alt="" class="book-detail-cover" loading="lazy">
        <div>
          <h3 class="book-detail-title">${book.title}</h3>
          <p class="book-detail-author">por ${book.author}</p>
          <div class="book-detail-meta">
            <span>📅 ${book.year}</span>
            <span>📄 ${book.pages} páginas</span>
            <span>🌐 ${book.language === 'pt' ? 'Português' : 'Inglês'}</span>
          </div>
          <p class="book-detail-description">${book.description}</p>
          <div class="book-detail-tags">
            ${book.genre.map(g => `<span class="card-tag">${g}</span>`).join('')}
          </div>
          <div class="book-detail-actions">
            ${book.downloadUrl !== '#' ? `<a href="${book.downloadUrl}" target="_blank" rel="noopener" class="btn btn-primary">⬇️ Baixar ePub</a>` : '<button class="btn btn-primary" disabled>⬇️ Indisponível</button>'}
            ${book.readUrl !== '#' ? `<a href="${book.readUrl}" target="_blank" rel="noopener" class="btn btn-outline">📖 Ler no Gutenberg</a>` : ''}
            ${state.user ? `
              ${inMyBooks ? `
                <button class="btn btn-secondary" onclick="closeModal(); updateProgress('${book.id}')">${progress >= 100 ? '↩️ Relere' : '📖 Continuar Lendo'}</button>
                <button class="btn btn-outline" onclick="removeFromMyBooks('${book.id}')">🗑️ Remover da Estante</button>
              ` : `
                <button class="btn btn-primary" onclick="addToMyBooks('${book.id}')">➕ Adicionar à Estante</button>
              `}
            ` : '<p style="color: var(--color-text-muted); font-size: var(--text-sm); margin-top: var(--space-3);">Faça login para adicionar à sua estante</p>'}
          </div>
        </div>
      </div>
    </div>
  `, 'book-detail-modal');
};

// --- Minha Estante ---
window.addToMyBooks = (bookId) => {
  if (!state.user) { showToast('Faça login primeiro', 'error'); renderView('auth'); return; }
  if (!state.readingProgress[bookId]) {
    state.readingProgress[bookId] = 0;
    saveState();
    showToast('Livro adicionado à sua estante!', 'success');
  }
  closeModal();
  renderView('my-books');
};

window.removeFromMyBooks = (bookId) => {
  delete state.readingProgress[bookId];
  saveState();
  showToast('Livro removido da estante', 'info');
  closeModal();
  renderView('my-books');
};

window.updateProgress = (bookId) => {
  if (!state.user) { showToast('Faça login primeiro', 'error'); return; }
  const book = state.books.find(b => b.id === bookId);
  if (!book) return;
  
  const current = state.readingProgress[bookId] || 0;
  
  openModal(`
    <header class="modal-header">
      <h2 class="modal-title" id="modalTitle">Atualizar Progresso: ${book.title}</h2>
      <button class="modal-close" aria-label="Fechar">✕</button>
    </header>
    <div class="modal-body">
      <form id="progressForm">
        <div class="form-group">
          <label class="form-label" for="progressInput">Página atual (0 a ${book.pages})</label>
          <input type="number" id="progressInput" class="form-input" min="0" max="${book.pages}" value="${Math.round(current * book.pages / 100)}" required>
        </div>
        <div class="form-group">
          <label class="form-label" for="progressPercent">Ou porcentagem (0-100%)</label>
          <input type="range" id="progressPercent" class="form-input" min="0" max="100" value="${current}" style="accent-color: var(--color-primary);">
          <output id="progressValue" style="display:block; text-align:center; font-weight:600; color: var(--color-primary); margin-top: var(--space-2);">${current}%</output>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-ghost" onclick="closeModal()">Cancelar</button>
          <button type="submit" class="btn btn-primary">${current >= 100 ? '↩️ Reiniciar' : 'Salvar Progresso'}</button>
        </div>
      </form>
    </div>
  `);
  
  const percentInput = $('#progressPercent');
  const pageInput = $('#progressInput');
  const valueOutput = $('#progressValue');
  
  percentInput.addEventListener('input', () => {
    valueOutput.textContent = percentInput.value + '%';
    pageInput.value = Math.round(percentInput.value * book.pages / 100);
  });
  
  pageInput.addEventListener('input', () => {
    const pct = Math.round(pageInput.value * 100 / book.pages);
    percentInput.value = Math.min(100, Math.max(0, pct));
    valueOutput.textContent = percentInput.value + '%';
  });
  
  $('#progressForm').addEventListener('submit', e => {
    e.preventDefault();
    const pct = parseInt(percentInput.value);
    state.readingProgress[bookId] = Math.min(100, Math.max(0, pct));
    saveState();
    showToast('Progresso atualizado: ' + state.readingProgress[bookId] + '%', 'success');
    closeModal();
    renderView('my-books');
  });
};

// --- Search ---
$('#searchToggle')?.addEventListener('click', () => {
  const bar = $('#searchBar');
  bar.classList.toggle('hidden');
  if (!bar.classList.contains('hidden')) $('#searchInput').focus();
});

let searchDebounce;
$('#searchInput')?.addEventListener('input', (e) => {
  clearTimeout(searchDebounce);
  searchDebounce = setTimeout(() => {
    state.searchQuery = e.target.value.trim().toLowerCase();
    renderSearchResults();
  }, 200);
});

const renderSearchResults = () => {
  const results = $('#searchResults');
  if (!state.searchQuery) { results.innerHTML = ''; return; }
  
  const matches = state.books.filter(b => 
    b.title.toLowerCase().includes(state.searchQuery) ||
    b.author.toLowerCase().includes(state.searchQuery) ||
    b.genre.some(g => g.toLowerCase().includes(state.searchQuery))
  ).slice(0, 8);
  
  if (matches.length === 0) {
    results.innerHTML = '<div style="padding: var(--space-4); color: var(--color-text-muted); text-align: center;">Nenhum resultado</div>';
    return;
  }
  
  results.innerHTML = matches.map(book => `
    <button class="search-result-item" onclick="closeSearch(); openBookDetail('${book.id}')" role="option">
      <img src="${book.cover}" alt="" class="search-result-cover" loading="lazy">
      <div class="search-result-info">
        <div class="search-result-title">${book.title}</div>
        <div class="search-result-author">${book.author}</div>
      </div>
    </button>
  `).join('');
};

const closeSearch = () => {
  $('#searchBar').classList.add('hidden');
  $('#searchInput').value = '';
  state.searchQuery = '';
  $('#searchResults').innerHTML = '';
};

document.addEventListener('click', e => {
  if (!e.target.closest('#searchBar') && !e.target.closest('#searchToggle')) closeSearch();
});

// --- Mobile Menu ---
$('#mobileMenuBtn')?.addEventListener('click', () => {
  $('#mobileMenuOverlay').classList.add('open');
  renderMobileNav();
});

$('#mobileMenuClose')?.addEventListener('click', () => {
  $('#mobileMenuOverlay').classList.remove('open');
});

$('#mobileMenuOverlay')?.addEventListener('click', e => {
  if (e.target === $('#mobileMenuOverlay')) $('#mobileMenuOverlay').classList.remove('open');
});

const renderMobileNav = () => {
  const nav = $('#mobileNav');
  const views = [
    { id: 'home', label: 'Início', icon: '🏠' },
    { id: 'library', label: 'Biblioteca', icon: '📖' },
    { id: 'book-of-week', label: 'Destaque da Semana', icon: '⭐' },
    { id: 'friends', label: 'Amigos', icon: '👥' },
    { id: 'clubs', label: 'Clubes', icon: '🏛️' }
  ];
  
  if (state.user) {
    views.push({ id: 'profile', label: 'Meu Perfil', icon: '👤' });
    views.push({ id: 'my-books', label: 'Minha Estante', icon: '📚' });
  } else {
    views.push({ id: 'auth', label: 'Entrar/Cadastrar', icon: '👤' });
  }
  
  nav.innerHTML = views.map(v => `
    <button class="mobile-nav-btn ${v.id === state.currentView ? 'active' : ''}" data-view="${v.id}">
      <span aria-hidden="true">${v.icon}</span>
      <span>${v.label}</span>
    </button>
  `).join('');
  
  $$('.mobile-nav-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      renderView(btn.dataset.view);
      $('#mobileMenuOverlay').classList.remove('open');
    });
  });
};

// --- Dropdown User Menu ---
document.addEventListener('click', e => {
  if (!e.target.closest('.user-menu')) $('#userDropdown')?.classList.add('hidden');
});

$('#logoutBtn')?.addEventListener('click', logout);

// --- Placeholders para funcionalidades futuras ---
window.openAddFriendModal = () => showToast('Funcionalidade em desenvolvimento', 'info');
window.openCreateClubModal = () => showToast('Funcionalidade em desenvolvimento', 'info');

// --- Inicialização ---
const init = () => {
  loadState();
  updateUserMenu();
  renderView('home');
  
  // Header scroll effect
  window.addEventListener('scroll', () => {
    $('.header')?.classList.toggle('scrolled', window.scrollY > 20);
  });
  
  // Keyboard navigation for modals
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
      closeModal();
      closeSearch();
      $('#mobileMenuOverlay')?.classList.remove('open');
    }
  });
  
  console.log('📚 Renilia Book inicializado!');
  console.log(`${state.books.length} livros carregados`);
  console.log(state.user ? `Logado como ${state.user.name}` : 'Visitante não logado');
};

// Iniciar quando DOM pronto
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', init);
} else {
  init();
}

// Exportar para uso no console (debug)
window.ReniliaBook = { state, BOOKS_DATA, showToast, openBookDetail };
